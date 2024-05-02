package models.entities.domain.persona;

import models.entities.domain.Persistente;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.georef.Localidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.notificaciones.InfoNotificacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.entities.domain.notificaciones.estrategia.Email.EstrategiaEmail;
import models.entities.domain.notificaciones.tiempo.Inmediata;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.sesion.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persona")
@Setter
@Getter
@NoArgsConstructor
public class Persona extends Persistente {
    @OneToMany(mappedBy = "persona", cascade = CascadeType.MERGE)
    private List<Miembro> miembros;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telefono")
    private String telefono;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    @Column(name = "puntos_confianza")
    private Float puntosConfianza;
    @Enumerated(EnumType.STRING)
    private GradoDeConfianza gradoDeConfianza;
    @ManyToOne // TODO no seria one to one
    @JoinColumn(name = "config_notificacion_id", referencedColumnName = "id")
    private ConfigNotificacion configNotificacion;
    @Embedded
    private Interes interes;
    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    Provincia provincia;
    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    Municipio municipio;
    @ManyToOne
    @JoinColumn(name = "localidad_id", referencedColumnName = "id")
    Localidad localidad;

    public Persona(Usuario usuario, String nombre, String apellido, String mail, String telefono, ConfigNotificacion config) {
        this.miembros = new ArrayList<>();
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.telefono = telefono;
        this.puntosConfianza = 5F;
        this.gradoDeConfianza = GradoDeConfianza.CONFIABLE_NIVEL_2;
        this.configNotificacion = config;
    }

    public void notificar(InfoNotificacion infoNotificacion) {
        configNotificacion.notificar(infoNotificacion);
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public Boolean yaEstaEnComunidad(Comunidad comunidad) {
        return miembros.stream().map(Miembro::getComunidad).anyMatch(c -> c.equals(comunidad));
    }

    public List<Incidente> abrirIncidente(String observaciones, PrestacionServicio prestacionServicio, Miembro ... miembros) {
        List<Incidente> incidentes = new ArrayList<>();
        for(Miembro miembro : miembros) {
            Incidente incidente = new Incidente(miembro.getComunidad(), observaciones, prestacionServicio, this);
            miembro.getComunidad().notificarAperturaIncidente(incidente);
            incidentes.add(incidente);
        }
        return incidentes;
    }

    public void eliminarMiembro(Miembro miembro) {
        this.miembros.remove(miembro);
    }
}
