Filevalidation = () => {
        const fi = document.getElementById('file');

        if (fi.files.length > 0) {
            for (const i = 0; i <= fi.files.length - 1; i++) {

                const fsize = fi.files.item(i).size;
                const file = Math.round((fsize / 1024));
                // The size of the file.
                if (file >= 5000) {
                    alert(
                        "Archivo demasiado grande, seleccione un archivo de menos de 5 MB");
                }else {
                    document.getElementById('size').innerHTML = '<b>'
                    + file + '</b> KB';
                }
            }
        }
    }