package ec.edu.ups.ppw.gproyecto.services;

import jakarta.ejb.Singleton;
import jakarta.ejb.Schedule;
import jakarta.inject.Inject;
import java.time.*;
import java.util.List;
import ec.edu.ups.ppw.gproyecto.Advisory;
import ec.edu.ups.ppw.gproyecto.Notification;
import ec.edu.ups.ppw.gproyecto.bussines.GestionAdvisories;
import ec.edu.ups.ppw.gproyecto.bussines.GestionNotifications;

@Singleton
public class RecordatorioScheduler {

    @Inject
    private GestionAdvisories ga;

    @Inject
    private GestionNotifications gn;

    @Inject
    private EmailService emailService;
    
    @Schedule(hour="*", minute="*", second="0", persistent=false)
    public void enviarRecordatorios() {

        System.out.println("⏰ Scheduler ejecutándose: " + LocalDateTime.now());

        LocalDateTime ahora = LocalDateTime.now();
        List<Advisory> asesorias = ga.getAdvisoriesConfirmadas();

        System.out.println("✔ Asesorías confirmadas: " + asesorias.size());

        for (Advisory a : asesorias) {

            LocalDateTime inicio = LocalDateTime.of(
                a.getFecha(),
                LocalTime.parse(a.getHora())
            );

            long segundos = Duration.between(ahora, inicio).getSeconds();

            System.out.println("🕒 Asesoría " + a.getId() + " en " + segundos + " segundos");

            if (segundos <= 300 && segundos > 0) {

                if (gn.existeRecordatorio(a.getId())) {
                    System.out.println("⛔ Recordatorio ya enviado");
                    continue;
                }

                System.out.println("📧 Enviando recordatorio a: " + a.getCorreoCliente());

                emailService.enviarCorreo(
                    a.getCorreoCliente(),
                    "⏰ Recordatorio de asesoría",
                    "Hola " + a.getNombreCliente() +
                    "\n\nTu asesoría inicia en 5 minutos.\nFecha: " +
                    a.getFecha() + " Hora: " + a.getHora()
                );

                Notification n = new Notification();
                n.setMensaje("⏰ Tu asesoría inicia en 5 minutos");
                n.setFecha(LocalDate.now());
                n.setLeido(false);
                n.setUser(a.getUser());
                n.setAdvisoryId(a.getId());

                try {
                    gn.crear(n);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("✅ Recordatorio enviado y notificación creada");
            }
        }
    }

}
