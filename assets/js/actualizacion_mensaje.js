/* 
 * Funcion principal
 */

$(document).ready(function () {
    $('form').change(function () {
        console.log(this.value);
        $("#mensaje").html("");
    });
});
