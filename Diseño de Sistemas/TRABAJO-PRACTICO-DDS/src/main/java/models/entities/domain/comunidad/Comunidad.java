package models.entities.domain.comunidad;

import java.util.ArrayList;
import java.util.List;

import models.entities.domain.Persistente;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.incidentes.SugerenciaRevision;
import models.entities.domain.notificaciones.DatosNotificacion;
import models.entities.domain.notificaciones.Notificador;
import models.entities.domain.notificaciones.tiempo.Inmediata;
import models.entities.domain.notificaciones.tiempo.TiempoNotificacion;
import models.entities.domain.persona.GradoDeConfianza;
import models.entities.domain.georef.CalculadorCercania;
import models.entities.domain.persona.Persona;
import models.entities.domain.georef.Ubicacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comunidad")
@Getter
@Setter
@NoArgsConstructor
public class Comunidad extends Persistente {
    @OneToMany(mappedBy = "comunidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<Miembro> miembros;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "comunidad_admin_id", referencedColumnName = "id")
    private List<Miembro> administradores;

    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    private GradoDeConfianza gradoDeConfianza;

    @Column(name = "activa")
    private Boolean activa;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.gradoDeConfianza = GradoDeConfianza.CONFIABLE_NIVEL_2;
        this.activa = true;
    }

    public void agregarNuevoMiembro(Persona persona, Boolean afectado) {
        if(personaYaEstaEnComunidad(persona)) {
            return;
        }
        Miembro miembro = new Miembro(this, persona, afectado);
        this.agregarMiembro(miembro);
        persona.agregarMiembro(miembro);
    }

    public void agregarMiembro(Miembro miembro) {
        this.miembros.add(miembro);
    }

    public void agregarAdministrador(Miembro miembro) {
        this.administradores.add(miembro);
    }

    public void sacarAdministrador(Miembro miembro) {
        this.administradores.remove(miembro);
    }

    public Boolean personaYaEstaEnComunidad(Persona persona) {
        return this.miembros.stream().anyMatch(miembro -> miembro.correspondeUsuario(persona));
    }

    public int calcularNumeroAfectados() {
        List<Miembro> listaAfectados = this.miembros.stream().filter(Miembro::getAfectado).toList();
        return listaAfectados.size();
    }

    public void notificarAperturaIncidente(Incidente incidente) {
        String mensaje = "Se abrio un incidente";
        String titulo = "Apertura de incidente";
        DatosNotificacion datos = crearDatosNotificacion(titulo, mensaje, null, incidente);
        List<Persona> destinatarios = miembros.stream().map(Miembro::getPersona).toList();
        Notificador.getInstance().notificar(datos, destinatarios);
    }

    public void notificarCierreIncidente(Incidente incidente) {
        String mensaje = "Se cerro un incidente";
        String titulo = "Cierre de incidente";
        DatosNotificacion datos = crearDatosNotificacion(titulo, mensaje, null, incidente);
        List<Persona> destinatarios = miembros.stream().map(Miembro::getPersona).toList();
        Notificador.getInstance().notificar(datos, destinatarios);
    }

    public DatosNotificacion crearDatosNotificacion(String titulo, String mensaje, TiempoNotificacion tiempoNotificacion, Incidente incidente) {
        return new DatosNotificacion(this, incidente, mensaje, titulo, tiempoNotificacion);
    }
}
