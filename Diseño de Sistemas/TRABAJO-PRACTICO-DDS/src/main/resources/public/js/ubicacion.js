// base_url = "http://localhost:8080/";
base_url = "https://grupo12dds.onrender.com/";

const provinciasSelect = document.getElementById("provincias-select");
const municipiosSelect = document.getElementById("municipios-select");
const localidadesSelect = document.getElementById("localidades-select");


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
    const opt = document.createElement("option");
    opt.value = "";
    opt.text = "";
    select.add(opt);
    datos.forEach((d) => {
        const option = document.createElement("option");
        option.value = d.id;
        option.text = d.descripcion;
        select.add(option);
    });
}

async function actualizarMunicipiosSelect() {
    if(provinciasSelect == undefined)
        return;
    const provinciasSeleccionada = provinciasSelect.value;
    if (provinciasSeleccionada == "") return;
    const url = base_url + "provincias/" + provinciasSeleccionada + "/municipios";
    await actualizarSelect(municipiosSelect, url);
    actualizarLocalidadesSelect();
}

function actualizarLocalidadesSelect() {
    if(municipiosSelect == undefined)
        return;
    const municipioSeleccionado = municipiosSelect.value;
    if (municipioSeleccionado == "") return;
    const url = base_url + "municipios/" + municipioSeleccionado + "/localidades";
    actualizarSelect(localidadesSelect, url);
}

$(document).ready(function () {
    $("#provincias-select")
        .on("change", function () {
            actualizarMunicipiosSelect();
        })
        .trigger("change");

    $("#municipios-select")
        .on("change", function () {
            actualizarLocalidadesSelect();
        })
        .trigger("change");
});
