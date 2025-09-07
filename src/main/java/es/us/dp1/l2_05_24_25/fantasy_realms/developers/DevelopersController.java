package es.us.dp1.l2_05_24_25.fantasy_realms.developers;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.maven.model.Developer;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.model.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los desarrolladores
 * del proyecto, incluyendo la obtención de la lista de desarrolladores registrada
 * en el archivo `pom.xml`.
 * 
 * Seguridad: Este controlador no requiere autenticación.
 */
@RestController
@RequestMapping("/api/v1/developers")
@Tag(name = "Developers", description = """
        Controlador REST que permite obtener la lista de desarrolladores registrada
        en el archivo `pom.xml`. Seguridad: Este controlador no requiere autenticación.
        """)
public class DevelopersController {
    List<Developer> developers;


    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    /**
     * Obtiene la lista de desarrolladores del proyecto. Si los datos no están cargados,
     * se procesan en tiempo de ejecución.
     *
     * @return Lista de objetos {@link Developer}.
     */
    @Operation(summary = "Obtener lista de desarrolladores",
               description = "Devuelve la lista de desarrolladores registrada en el archivo `pom.xml`. "
                           + "Si la lista no está cargada, se procesa dinámicamente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de desarrolladores obtenida con éxito",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Developer.class))),
        @ApiResponse(responseCode = "500", description = """
            Error al intentar leer el archivo `pom.xml`.
            """, content = @Content)
    })
    @GetMapping()
    public List<Developer> getDevelopers(){
        if(developers==null)
            loadDevelopers();
        return developers;        
    }

    private void loadDevelopers(){        
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader("pom.xml"));
            Person p=null;
            developers=model.getDevelopers();                                            
        } catch (IOException | XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


}
