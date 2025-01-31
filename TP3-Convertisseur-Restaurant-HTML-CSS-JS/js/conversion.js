//creer une seule fonction a la place des 4 fonctions

// function versCm() {
//     var cm = document.getElementById("value").value;
//     cm = cm * 2.5;
//     document.getElementById("res").innerHTML = "pouces ==> " + cm + "cm";
// }
// function versPouce() {
//     var P = document.getElementById("value").value;
//     p = P * 0.4;
//     var contenu = document.getElementById("res").innerHTML = "cm ==> " + p + " pouces";
// }
// function versFarenheit() {
//     var f = document.getElementById("value").value;
//     f = ((f * 1.8) + 32).toFixed(1);
//     var contenu = document.getElementById("res").innerHTML = "celcius ==> " + f + " farenheit";
// }
// function versCelcius() {
//     var c = document.getElementById("value").value;
//      c = ((c - 32) * 5 / 9).toFixed(1);
//     var contenu = document.getElementById("res").innerHTML = "farenheit ==> " + c + " celcius";

// }
function convertir(message) {
    var val = document.getElementById("value").value;
    var res;
    if (message === "pouces vers cm") {
        res = val * 2.5;
        document.getElementById("res").innerHTML = "pouces ==> " + res + "cm";
    }
    else if (message === "cm vers pouce") {
        res = (val * 0.4).toFixed(2);
        document.getElementById("res").innerHTML = "cm ==> " + res + " pouces";
    }
    else if (message === "celcius vers farenheit") {
        res = ((val * 1.8) + 32).toFixed(1);
        document.getElementById("res").innerHTML = "celcius ==> " + res + " farenheit";
    }
    else if (message === "farenheit vers celcius") {
        res = ((val - 32) * 5 / 9).toFixed(1);
        document.getElementById("res").innerHTML = "farenheit ==> " + res + " celcius";
    }





}