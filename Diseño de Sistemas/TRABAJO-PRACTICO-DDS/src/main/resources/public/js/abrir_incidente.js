base_url = "https://grupo12dds.onrender.com/";

const entidadesSelect = document.getElementById("entidades-select");
const establecimientosSelect = document.getElementById("establecimientos-select");
const serviciosSelect = document.getElementById("servicios-select");

// Función para actualizar las opciones del segundo select
async function actualizarSelect(select, urlFetch) {
    let datos;
    await fetch(urlFetch)
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Error de red - Código: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            datos = data;
        })
        .catch((error) => {
            console.error("Hubo un error:", error);
        });

    // Limpiar las opciones anteriores
    select.innerHTML = "";

    if (datos == undefined) return null;
    // Agregar las nuevas opciones
    datos.forEach((d) => {
        const option = document.createElement("option");
        option.value = d.id;
        if(d.descripcion == null)
            option.text = d.nombre;
        else
            option.text = d.nombre + " - " + d.descripcion;
        select.add(option);
    });
}

async function actualizarEstablecimientosSelect() {
    if(entidadesSelect == undefined)
        return;
    const entidadSeleccionada = entidadesSelect.value;
    if (entidadSeleccionada == "") return;
    const url = base_url +
        "entidades/" +
        entidadSeleccionada +
        "/establecimientos";
    await actualizarSelect(establecimientosSelect, url);
    actualizarServiciosSelect();
}

function actualizarServiciosSelect() {
    if(establecimientosSelect == undefined)
        return;
    const establecimientoSeleccionado = establecimientosSelect.value;
    if (establecimientoSeleccionado == "") return;
    const url = base_url +
        "establecimientos/" +
        establecimientoSeleccionado +
        "/servicios";
    actualizarSelect(serviciosSelect, url);
}

$(document).ready(function () {
    $(".js-basic-multiple").select2();
    $(".js-basic-single").select2();

    $("#entidades-select")
        .on("change", function () {
            actualizarEstablecimientosSelect();
        })
        .trigger("change");

    $("#establecimientos-select")
        .on("change", function () {
            actualizarServiciosSelect();
        })
        .trigger("change");
});
