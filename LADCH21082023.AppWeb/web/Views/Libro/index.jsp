<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
          <title>Biblioteca</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container center-align">
            <div>
                <br>
                <br>
                <br>
                <br>
                <br>
                <h2><Strong>Biblioteca</Strong></h2>
                <p>
                    Registrar un nuevo libro
                </p>
                    <a href="Libro?accion=create" class="waves-effect waves-light btn green"><i class="material-icons right">add</i>Registrar</a>
                <br>
                <br>
                <p>
                    Libros registrados
                </p>
                    <a href="Book" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Ver listado</a>
            </div>
        </main>
    </body>
</html>
