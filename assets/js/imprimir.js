$( document ).ready( function(){
    $("#detalle").css({
        "width": "500px",
        "min-width": "400px", 
        "max-width": "600px"
    });
    $(".table").css({
        "width": "500px",
        "min-width": "400px", 
        "max-width": "600px"
    });
});
function imprimir(div) {
    var printContents = document.getElementById(div).innerHTML;
    var originalContents = document.body.innerHTML;
    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
}

