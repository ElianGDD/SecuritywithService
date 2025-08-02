package com.risosu.EDesalesProgramacionNCapasJunio3.RequestController;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaJPARespositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionJPAREPOSITORYDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoJPARepositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.IUsuarioJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.MunicipioJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.MunicipioJPARepositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.PaisJPADAOImplementatio;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.PaisJPARepositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.RolJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.RollJPARepositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.UsuarioJPAREPOSITORYDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaJPARespositoryDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionService;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.JWTService;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.UsuarioService;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.ResultValidarDatos;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.AuthRequest;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Usuario", description = "Operaciones relacionadas con usuarios y direcciones")
@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    @Autowired
    IUsuarioJPADAOImplementation iUsuarioJPADAOImplementation;
    @Autowired
    DireccionJPADAOImplementation direccionJPADAOImplementation;
    @Autowired
    RolJPADAOImplementation rolJPADAOImplementation;
    @Autowired
    PaisJPADAOImplementatio paisJPADAOImplementatio;
    @Autowired
    EstadoJPADAOImplementation estadoJPADAOImplementation;
    @Autowired
    MunicipioJPADAOImplementation municipioJPADAOImplementation;
    @Autowired
    ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    @Autowired
    UsuarioJPAREPOSITORYDAO usuarioJPAREPOSITORYDAO;
    @Autowired
    DireccionJPAREPOSITORYDAO direccionJPAREPOSITORYDAO;
    @Autowired
    RollJPARepositoryDAO rollJPARepositoryDAO;
    @Autowired
    PaisJPARepositoryDAO paisJPARepositoryDAO;
    @Autowired
    EstadoJPARepositoryDAO estadoJPARepositoryDAO;
    @Autowired
    MunicipioJPARepositoryDAO municipioJPARepositoryDAO;
    @Autowired
    ColoniaJPARespositoryDAO coloniaJPARespositoryDAO;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    JWTService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private DireccionService direccionService;

    @Operation(
            summary = "Obtiene todos los usuarios",
            description = "Este endpoint devuelve una lista de todos los usuarios y sus direcciones registrados en el sistema"
    )
    @GetMapping
    public ResponseEntity GetAll() {
        Result result = new Result();

        try {
            List<Usuario> usuarios = usuarioJPAREPOSITORYDAO.findAll();
            List<UsuarioDireccion> resultados = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = usuario;
                usuarioDireccion.Direcciones = direccionJPAREPOSITORYDAO.findByUsuario_IdUsuario(usuario.getIdUsuario());
                resultados.add(usuarioDireccion);
            }

            if (resultados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            result.objects = new ArrayList<>();
            result.objects.addAll(resultados);
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene un usuario",
            description = "Este endpoint devuelve un usuario y sus direcciones registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Usuario")
    public ResponseEntity GetUsuario(@RequestParam int IdUsuario) {
        Result result = new Result();

        try {
            Optional<Usuario> usuarioOptional = usuarioJPAREPOSITORYDAO.findById(IdUsuario);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Usuario usuario = usuarioOptional.get();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = usuario;

            result.object = usuarioDireccion;
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene una direccion",
            description = "Este endpoint devuelve una direccion por un id direccion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Direcciones encontradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Direccion/{IdUsuario}")
    public ResponseEntity GetDireccionByUsuario(@PathVariable int IdUsuario) {

        Result result = new Result();

        try {
            Optional<Usuario> usuarioOptional = usuarioJPAREPOSITORYDAO.findById(IdUsuario);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Usuario usuario = usuarioOptional.get();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = usuario;
            usuarioDireccion.Direcciones = direccionJPAREPOSITORYDAO.findByUsuario_IdUsuario(usuario.getIdUsuario());

            result.object = usuarioDireccion;
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

    }

    @Operation(
            summary = "Obtiene una direccion",
            description = "Este endpoint devuelve una direccion por una id de direccion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección encontrada"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/DireccionByID/{IdDireccion}")
    public ResponseEntity GetDireccionByDireccion(@PathVariable int IdDireccion) {
        Result result = new Result();

        try {
            Optional<Direccion> direccionOptional = direccionJPAREPOSITORYDAO.findById(IdDireccion);

            if (direccionOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Direccion direccion = direccionOptional.get();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Direccion = direccion;

            result.object = usuarioDireccion;
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene todos los roles",
            description = "Este endpoint devuelve una lista de todos los roles"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estados obtenida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Roll")
    public ResponseEntity GetAllRoll() {
        Result result = new Result();

        try {
            List<Roll> rollList = rollJPARepositoryDAO.findAll();

            result.objects = new ArrayList<>();
            result.objects.addAll(rollList);
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene todos los paises",
            description = "Este endpoint devuelve una lista de todos los paises"
    )
    @GetMapping("/Pais")
    public ResponseEntity GetAllPais() {
        Result result = new Result();

        try {
            List<Pais> paisList = paisJPARepositoryDAO.findAll();
            result.objects = new ArrayList<>();
            result.objects.addAll(paisList);
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene estador por id pais",
            description = "Este endpoint devuelve una lista con todos los estados que tienen la misma id pais"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estados obtenida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Estado/{IdPais}")
    public ResponseEntity GetEstadoByPais(@PathVariable int IdPais) {
        Result result = new Result();
        try {
            List<Estado> estadosList = estadoJPARepositoryDAO.findByPais_IdPais(IdPais);

            result.objects = new ArrayList<>();
            result.objects.addAll(estadosList);
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene municipios por id estado",
            description = "Este endpoint devuelve una lista de municipios que tienen el mismo idestado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de municipios obtenida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Municipio/{IdEstado}")
    public ResponseEntity GetMunicipioByEstado(@PathVariable int IdEstado) {
        Result result = new Result();

        try {
            List<Municipio> municipioList = municipioJPARepositoryDAO.findByEstado_IdEstado(IdEstado);

            result.objects = new ArrayList<>();
            result.objects.addAll(municipioList);
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene una lista de colonias",
            description = "Este endpoint devuelve una lista de colonias que sus id de municipio sean las mismas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de colonias obtenida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/Colonia/{IdMunicipio}")
    public ResponseEntity GetColoniaByMunicipio(@PathVariable int IdMunicipio) {
        Result result = new Result();
        try {

            List<Colonia> coloniaList = coloniaJPARespositoryDAO.findByMunicipio_IdMunicipio(IdMunicipio);

            result.objects = new ArrayList<>();
            result.objects.addAll(coloniaList);
            result.correct = true;

            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Obtiene todos los usuarios",
            description = "Este endpoint devuelve una lista de todos los usuarios y sus direcciones registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colonias encontradas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/ColoniaCP/{CP}")
    public ResponseEntity GetColoniaMEPByCP(@PathVariable String CP) {
        Result result = new Result();
        try {
            List<Colonia> coloniaList = coloniaJPARespositoryDAO.findByCodigoPostal(CP);

            result.objects = new ArrayList<>();
            result.objects.addAll(coloniaList);
            result.correct = false;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        }
    }

    @Operation(
            summary = "Agrega un nuevo usuario",
            description = "Este endpoint agrega un nuevo usuario junto a una direccion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al crear el usuario")
    })
    @PostMapping("/Usuario")
    public ResponseEntity PostUsuarioAll(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto que contiene los datos del usuario y su dirección",
                    required = true)
            @RequestBody UsuarioDireccion usuarioDireccion) {

        try {
            UsuarioDireccion creado
                    = usuarioService.crearConDirecciones(usuarioDireccion);

            Result result = new Result();
            result.objects = List.of(creado);
            result.correct = true;
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result);

        } catch (Exception ex) {
            Result error = new Result();
            error.correct = false;
            error.errorMessage = ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }

    @Operation(
            summary = "Agrega una nueva direccion a usuario",
            description = "Este endpoint agrega una nueva dirección y se le agrega un id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dirección agregada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/Direccion")
    public ResponseEntity PostDireccionByUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto que contiene el usuario y la nueva dirección",
                    required = true
            )
            @RequestBody UsuarioDireccion usuarioDireccion) {

        Result result = direccionService
                .POSTDireccionByIdUsuario(usuarioDireccion);

        if (result.correct) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result.object);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result.errorMessage);
        }
    }

    @Operation(
            summary = "Se hace una busqueda entre los usuarios",
            description = "Este endpoint devuelve una lista de los usuarios y sus direcciones usando como parametros de busqueda con los parametros de nombre, apellidopaterno,apellidomarerno y idroll"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios con esos parámetros"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/BusquedaDinamica")
    public ResponseEntity busquedaDinamica(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Parámetros de búsqueda: nombre, apellido paterno, apellido materno, rol",
                    required = true
            )
            @RequestBody Usuario filtro) {

        Result result = new Result();
        try {
            Integer idRoll = filtro.getRoll() != null
                    ? filtro.getRoll().getIdRoll()
                    : null;

            List<Usuario> usuarios = usuarioJPAREPOSITORYDAO.buscarDinamico(
                    filtro.getNombre(),
                    filtro.getApellidoPaterno(),
                    filtro.getApellidoMaterno(),
                    idRoll
            );

            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            result.objects = new ArrayList<>(usuarios);
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
    }

    @Operation(
            summary = "Se agregan un grupo grande de usuarios",
            description = "Este endpoint devuelve una lista de todos los usuarios y sus direcciones registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios cargados exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al procesar la carga masiva")
    })
    @PostMapping("/CargaMasiva")
    public ResponseEntity PostCargaMasiva(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de objetos UsuarioDireccion para registrar en la base de datos",
                    required = true
            )
            @RequestBody List<UsuarioDireccion> usuarioDireccion) {
        Result result = new Result();
        try {
            // Insertar en BD
            result = iUsuarioJPADAOImplementation.PostCargaMasiva(usuarioDireccion);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error interno al procesar el archivo";
            result.ex = ex;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @Operation(
            summary = "Se actualiza un usuario",
            description = "Este endpoint actualiza un usuario pasandole todos sus datos"
    )
    @PutMapping("/usuario")
    public ResponseEntity PutAll(@RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            Result result = iUsuarioJPADAOImplementation.PutAll(usuarioDireccion);

            if (result.correct) {
                if (result.object != null) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(result.object);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Usuario creado pero no retornado.");
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }

    }

    @Operation(
            summary = "Se actualiza un usuario",
            description = "Este endpoint actualiza un usuario pasandole todos sus datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
        @ApiResponse(responseCode = "500", description = "Error interno al actualizar")
    })
    @PutMapping("/Usuario")
    public ResponseEntity PUTUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto con los nuevos datos del usuario y su dirección",
                    required = true
            )
            @RequestBody Usuario usuario) {

        Result result = new Result();
        try {
            Usuario actualizado = usuarioJPAREPOSITORYDAO.save(usuario);

            result.objects = List.of(actualizado);
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getMessage();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
    }

    @Operation(
            summary = "Se actualiza una direccion",
            description = "Este endpoint actualiza una direccion del usuario pasandole la id de dirección para seleccionarla"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno al actualizar la dirección")
    })
    @PutMapping("/Direccion")
    public ResponseEntity PUTDireccionByUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la dirección actualizados, incluyendo su ID",
                    required = true
            )
            @RequestBody Direccion direccion) {
        Result result = new Result();
        try {

            Direccion direccionActualizada = direccionJPAREPOSITORYDAO.save(direccion);
            result.objects = List.of(direccionActualizada);
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
    }

    @Operation(
            summary = "Borra a un usuario",
            description = "Este endpoint borra a un usuario y sus direcciones registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario y direcciones eliminados exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
    })
    @DeleteMapping("/usuario/{idUsuario}")
    @Transactional
    public ResponseEntity DELETEALL(
            @Parameter(description = "ID del usuario que se desea eliminar", required = true, example = "1")
            @PathVariable int idUsuario) {

        Result result = new Result();
        try {
            long direccionesEliminadas = direccionJPAREPOSITORYDAO.deleteByUsuario_IdUsuario(idUsuario);
            usuarioJPAREPOSITORYDAO.deleteById(idUsuario);
            result.correct = true;

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            System.err.println("Excepción en DELETEALL: " + ex.getMessage());
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al eliminar: " + ex.getLocalizedMessage());

        }
    }

    @Operation(
            summary = "Borra una direccion",
            description = "Este endpoint borra una direccion oir medio de su id pasado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dirección eliminada exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno al eliminar la dirección")
    })
    @DeleteMapping("/Direccion")
    public ResponseEntity DELEDireccion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto que contiene la dirección con su ID",
                    required = true
            )
            @RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            direccionJPAREPOSITORYDAO.deleteById(usuarioDireccion.Direccion.getIdDireccion());
            result.correct = true;
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            System.err.println("Excepción en DELEDireccion: " + ex.getMessage());
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    public DemoRestController(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getNombre(),
                            authRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> claims = new HashMap<>();
            claims.put("role", userDetails.getAuthorities());

            String token = jwtService.generateToken(userDetails.getUsername(), claims);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/ProcesarCargaMasiva")
    public ResponseEntity procesarCargaMasiva(@RequestParam("archivo") MultipartFile archivo) {
        Result result = new Result();

        try {
            File tempFile = File.createTempFile("archivo", ".txt");
            archivo.transferTo(tempFile);

            List<UsuarioDireccion> usuarios = LecturaArchivoTXT(tempFile);

            if (usuarios == null || usuarios.isEmpty()) {
                result.correct = false;
                result.errorMessage = "El archivo está vacío o no contiene datos válidos.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

            result = usuarioService.PostCargaMasiva(usuarios);

            return result.correct
                    ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error procesando archivo: " + ex.getMessage();
            result.ex = ex;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping("Lectura")
    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {

        List<UsuarioDireccion> usuarioDireccionList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {
            bufferedReader.readLine(); // Saltar encabezado
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                Usuario usuario = new Usuario();
                usuario.setNombre(datos[0]);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    String fechaTexto = datos[1].trim();
                    usuario.setFechaNacimiento(fechaTexto.isEmpty() ? null : formatoFecha.parse(fechaTexto));
                } catch (ParseException e) {
                    usuario.setFechaNacimiento(null);
                }

                usuario.setUserName(datos[2]);
                usuario.setApellidoPaterno(datos[3]);
                usuario.setApellidoMaterno(datos[4]);
                usuario.setEmail(datos[5]);
                usuario.setPassword(datos[6]);
                usuario.setSexo(datos[7].isEmpty() ? '\0' : datos[7].charAt(0));
                usuario.setTelefono(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCurp(datos[10]);

                Roll roll = new Roll();
                roll.setIdRoll(Integer.parseInt(datos[11]));
                usuario.setRoll(roll);

                usuario.setImagenPerfil(datos[12]);
                usuario.setStatus(Integer.parseInt(datos[13]));

                // Crear y poblar Direccion
                Direccion direccion = new Direccion();
                direccion.setCalle(datos[14]);
                direccion.setNumeroInterior(datos[15]);
                direccion.setNumeroExterior(datos[16]);
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(Integer.parseInt(datos[17]));

                // Asociar Usuario y Direccion al contenedor UsuarioDireccion
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.setUsuario(usuario);
                usuarioDireccion.setDireccion(direccion);

                usuarioDireccionList.add(usuarioDireccion);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            usuarioDireccionList = null;
        }

        return usuarioDireccionList;
    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setNombre(row.getCell(0) != null ? row.getCell(0).toString() : "");
                usuarioDireccion.usuario.setFechaNacimiento(row.getCell(1) != null ? row.getCell(1).getDateCellValue() : null);
                usuarioDireccion.usuario.setUserName(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuarioDireccion.usuario.setApellidoPaterno(row.getCell(3) != null ? row.getCell(3).toString() : "");
                usuarioDireccion.usuario.setApellidoMaterno(row.getCell(4) != null ? row.getCell(4).toString() : "");
                usuarioDireccion.usuario.setEmail(row.getCell(5) != null ? row.getCell(5).toString() : "");
                usuarioDireccion.usuario.setPassword(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuarioDireccion.usuario.setSexo(row.getCell(7) != null ? row.getCell(7).toString().charAt(0) : ' ');
                usuarioDireccion.usuario.setTelefono(row.getCell(8) != null ? row.getCell(8).toString() : "");
                usuarioDireccion.usuario.setCelular(row.getCell(9) != null ? row.getCell(9).toString() : "");
                usuarioDireccion.usuario.setCurp(row.getCell(10) != null ? row.getCell(10).toString() : "");

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll((int) row.getCell(11).getNumericCellValue());

                usuarioDireccion.usuario.setImagenPerfil(row.getCell(12) != null ? row.getCell(12).toString() : "");
                usuarioDireccion.usuario.setStatus((int) row.getCell(13).getNumericCellValue());

                usuariosDireccion.add(usuarioDireccion);

            }
        } catch (Exception ex) {
            System.out.println("Errores en apartura de archivo");
        }

        return usuariosDireccion;
    }

    private List<ResultValidarDatos> ValidarDatos(List<UsuarioDireccion> usuarios) {
        List<ResultValidarDatos> listaErrores = new ArrayList<>();
        int fila = 1;
        if (usuarios == null) {
            listaErrores.add(new ResultValidarDatos(0, "Lista inexistente", "Lista inexistente"));

        } else if (usuarios.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0, "Lista vacia", "Lista vacia"));

        } else {
            for (UsuarioDireccion usuarioDireccion : usuarios) {
//                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
//                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getNombre(), "Campo obligatorio"));
//
//                }
                fila++;
            }
        }
        return listaErrores;
    }

}
