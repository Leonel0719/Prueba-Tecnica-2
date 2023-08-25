<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ladch21082023.entidades.Librp"%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Registrar libro</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <br>
            <br>
            <br>
            <h4><strong>Registrar libro</strong></h4>
            <form class="center-align" action="Book" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col m6 s12">
                        <input  id="txtTitle" type="text" name="title" required class="validate" maxlength="30">
                        <label for="txtTitle">Nombre</label>
                    </div>
                    <div class="input-field col m6 s12">
                        <input  id="txtAuthor" type="text" name="author" required class="validate" maxlength="80">
                        <label for="txtAuthor">Autor</label>
                    </div>
                    <div class="input-field col m4 s12">
                        <input  id="txtYear" type="number" name="year" required class="validate" maxlength="4">
                        <label for="txtYear">Año de publicación</label>
                    </div>
                    
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Book" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>