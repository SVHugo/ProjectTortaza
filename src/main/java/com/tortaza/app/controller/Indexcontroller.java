package com.tortaza.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.tortaza.app.helper.Message;
import com.tortaza.app.interfaces.IPedido;
import com.tortaza.app.interfaces.IPedidoDAO;
import com.tortaza.app.models.*;
import com.tortaza.app.service.*;
import com.tortaza.app.services.CarritoProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@SessionAttributes({ "usuario", "productos" })
public class Indexcontroller {

	@ModelAttribute("usuario")
	public Usuario new_usuario() {
		Usuario usuariop = new Usuario();
		return usuariop;
	}

	int idu = 0;

	@ModelAttribute("carrito")
	public List<ItemCarrito> new_carrito() {
		CarritoProducto carrito = CarritoProducto.getInstance();
		return carrito.getCarrito().getItems();
	}

	@ModelAttribute("subtotal")
	public double new_subt() {
		CarritoProducto carrito = CarritoProducto.getInstance();
		return carrito.getCarrito().getTotal();
	}
	/***************************************/
	@Autowired // inyeccion
	private UsuarioService iusuario;
	
	@Autowired
	private RolService irol;
	
	/***************************************/

	@Autowired // inyeccion
	private ProductoService iproducto;

	@Autowired // inyeccion
	private PedidoService ipedido;

	@Autowired // inyeccion
	private DetalleService idetalle;

	@Autowired
	private IPedidoDAO pedidoDAO;

	// MENU
	@GetMapping({ "/menu", "", "/" })
	public String Menu(Model model) {
		return "menu";
	}
	// --------------------------------------------------------------------//
	// LOGIN

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}

	// --------------------------------------------------------------------//
	// loguearse
	@PostMapping("/login")
	public String loguear( Usuario u, Model model) {
		List<Usuario> Aus = iusuario.listar(); // lista los usuarios
		Usuario usu = new Usuario();// paso1
		if (iusuario.validU(u)) { // verifica la autentificacion de los datos que vienen de la vista loguearse
			for (var users : Aus) {
				if (u.getCorreo().equals(users.getCorreo())) {
					usu = iusuario.findById(users.getId_usuario());
					usu.setEstadousuario(true);
					model.addAttribute("usuario", usu);
					idu = usu.getId_usuario();
					return "redirect:perfil";
				}
			}
			/*if (usu.getId_tipousuario() == 1) {
				model.addAttribute("usuario", usu);

				idu = usu.getId_usuario();
				return "redirect:perfil";
			} else {
				model.addAttribute("usuario", usu);
				idu = usu.getId_usuario();
				return "redirect:/administrar/productos";
			}*/

		}

		model.addAttribute("mensaje", "Usuario y/o contraseña incorrectas");
		model.addAttribute("titulo", "Login");

		return "login";
	}
	// --------------------------------------------------------------------//

	// Salir
	@GetMapping("/salir")
	public String salir(Model model) {
		model.addAttribute("usuario", new Usuario());
		
		return "redirect:/menu";
	}

	// REGISTRARSE
	@PostMapping("/registrarse")
	public String registrarse(@Valid Usuario us, Model model) {
		//us.setId_tipousuario(1); // cuando pongamos los datos
		us.setEstadousuario(false); // chancara a los valores por
		//us.setDni(00000000);
		iusuario.guardar(us);
		model.addAttribute("mensaje", "Ingrese con su usuario y contraseña");
		return "redirect:/login";
	}

	// VER PRODUCTOS
	@GetMapping("/productos/{cod}")
	public String categorias(@PathVariable int cod, Model model) {
		List<Producto> p = new ArrayList<>();
		for (var pro : iproducto.listar()) {
			if (pro.getId_categoria().equals(cod)) {
				p.add(pro);
			}
		}
		model.addAttribute("categoria", p);
		return "productos";
	}

	// VER PRODUCTO
	@GetMapping("/producto/{cod}")
	public String producto(@PathVariable int cod, Model model) {
		Producto p = iproducto.listarId(cod).get();
		model.addAttribute("producto", p);
		return "producto";
	}

	// PERFIL
	@GetMapping("/perfil")
	public String perfil(Model model) {
		return "perfil";
	}

	// PEDIDOS
	@GetMapping("/pedidos")
	public String pedidos(Model model) {
		// iusuario.listar().get(1).getPedido().get(1).getDetalle().get(1).getProducto().getNombre()
		// iusuario.listar().get(1).getPedido().get(1).
		if (idu != 0) {
			model.addAttribute("usuario", pedidoDAO.getById(idu));

		}

		model.addAttribute("test", "test");
		return "pedidos";
	}

	// DETALLE DE PEDIDOS
	@GetMapping("/pedidos/{cod}")
	public String detallepedidos(@PathVariable int cod, Model model) {
		ipedido.listarId(cod).get().getDetalle();

		model.addAttribute("detallepedido", ipedido.listarId(cod).get().getDetalle());
		return "pedidos";
	}

	// EDITAR PERFIL
	@GetMapping("/editarperfil")
	public String editarperfil(Model model) {

		return "editarperfil";
	}

	// GUARDAR PERFIL
	@PostMapping("/guardarperfil")
	public String guardarperfil(@Valid Usuario usuario, Model model) {
		iusuario.guardar(usuario);
		return "redirect:/perfil";
	}

	// --------------------------------------------------------------------//

	// CARRITO
	@GetMapping("/carrito")
	public String carrito(Model model) {
		CarritoProducto carrito = CarritoProducto.getInstance();
		model.addAttribute("subtotales", carrito.getCarrito().getTotal());
		return "carrito";
	}

	@GetMapping("/carrito/agregar/{cod}")
	public String agregar(@PathVariable int cod, Model model) {
		List<Producto> productos = new ArrayList<>();

		CarritoProducto carrito;

		List<ItemCarrito> items = new ArrayList<>();
		double subtotal = 0;

		try {

			carrito = CarritoProducto.getInstance();

			Producto p = iproducto.listarId(cod).get();
			if (p != null) {
				carrito.add(p);
			}
			productos = iproducto.listar();
			if (carrito != null) {
				items = carrito.getCarrito().getItems();
				subtotal = carrito.getCarrito().getTotal();
			}
		} catch (Exception ex) {
		}

		model.addAttribute("subtotales", subtotal);
		model.addAttribute("carrito", items);
		return "carrito";
	}

	@GetMapping("/carrito/quitar/{cod}")
	public String quitar(@PathVariable int cod, Model model) {
		List<Producto> productos = new ArrayList<Producto>();

		CarritoProducto carrito;

		List<ItemCarrito> items = new ArrayList<>();
		double subtotal = 0;

		try {
			carrito = CarritoProducto.getInstance();

			Producto p = iproducto.listarId(cod).get();
			if (p != null) {
				carrito.remove(p);
			}
			productos = iproducto.listar();
			if (carrito != null) {
				items = carrito.getCarrito().getItems();
				subtotal = carrito.getCarrito().getTotal();

			}
		} catch (Exception ex) {
		}

		model.addAttribute("subtotales", subtotal);
		model.addAttribute("carrito", items);
		return "carrito";
	}

	// --------------------------------------------------------------------//

	// CROMPRAR
	@GetMapping("/comprando")
	public String finalizar(Model model) {
		CarritoProducto carrito = CarritoProducto.getInstance();
		List<ItemCarrito> items = carrito.getCarrito().getItems();
		Usuario usuario = iusuario.findById(idu);
		Pedido pedido = new Pedido();
		pedido.setDireccioncliente(usuario.getDireccion_inicial());
		pedido.setEstadopago(true);
		pedido.setEstadopedido(true);
		pedido.setUsuario(usuario);
		Detallepedido detalle = new Detallepedido();
		List<Detallepedido> detallepedido = new ArrayList<>();
		double total = 0;
		for (var item : items) {
			Detallepedido producto = new Detallepedido();
			producto.setProducto(item.getProducto());
			producto.setCantidad(item.getCantidad());
			producto.setSubtotal(item.getCantidad() * producto.getProducto().getPrecio());
			producto.setPedido(pedido);
			detallepedido.add(producto);
			total += producto.getSubtotal();
		}
		pedido.setTotal(total);
		pedido.setDetalle(detallepedido);
		ipedido.guardar(pedido);
		idetalle.guardar(detallepedido);

		model.addAttribute("subtotales", carrito.getCarrito().getTotal());
		return "redirect:/pedidos";
	}

	// PROCESAR COMPRA

	@GetMapping("/procesar")
	public String procesar(Model model) {

		List<ItemCarrito> items = new ArrayList<>();

		CarritoProducto carrito = CarritoProducto.getInstance();

		double subtotal = 0;

		if (carrito != null) {
			items = carrito.getCarrito().getItems();
			subtotal = carrito.getCarrito().getTotal();
		}

		model.addAttribute("subtotales", subtotal);
		model.addAttribute("carrito", items);
		return "procesar";
	}

	// --------------------------------------------------------------------//

	// MANTENIMIENTO PRODUCTOS
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("/administrar/productos")
	public String mantenpro(Model model) {
		
		List<Producto> productos = iproducto.listar();
		List<Producto> productosdes = new ArrayList<>();
		List<Producto> productosbloq = new ArrayList<>();
		for (var p : productos) {
			if (p.isEstadoproducto()) {
				productosdes.add(p);
			} else {
				productosbloq.add(p);
			}
		}
		model.addAttribute("Productosbloq", productosbloq);
		model.addAttribute("Productosdes", productosdes);
		return "mantenimientopro";
	}

	// MANTENIMIENTO PRODUCTO NUEVO
	@GetMapping("/administrar/productos/nuevo")
	public String agregar(Model model) {
		model.addAttribute("Producto", new Producto());
		return "crearpro";
	}

	// MANTENIMIENTO PRODUCTO AGREGAR Y GUARDAR PARA EL EDITAR
	@PostMapping("/nuevoproducto")
	public String guardarp(@Validated @ModelAttribute("Producto") Producto p, BindingResult bindingResult, Model model,
			@RequestParam("img") MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("Producto",p);
			return "crearpro";
		}
		// imgen
		if (p.getId_producto() == null) {
			String nombreImagen = upload.saveImage(file);
			p.setImagen(nombreImagen);

		} else {
			if (file.isEmpty()) {
				Producto pr = new Producto();
				pr = iproducto.listarId(p.getId_producto()).get();
				p.setImagen(pr.getImagen());
			} else {
				String nombreImagen = upload.saveImage(file);
				p.setImagen(nombreImagen);

			}
		}
		p.setEstadoproducto(true);// false= no registra
		model.addAttribute("message",new Message("El producto se a añadido correctamente.","success"));
        iproducto.guardar(p);
        model.addAttribute("Producto", new Producto());
		return "crearpro";
	}

	// MANTENIMIENTO PRODUCTO EDITAR
	@GetMapping("/editar/{id}")
	public String editarp(@PathVariable int id, Model model) {
		Optional<Producto> producto = iproducto.listarId(id);
		model.addAttribute("producto", producto);
		return "editarpro";
	}
	
	@PostMapping("/actualizarproducto")
	public String actualizarp(@Validated @ModelAttribute("producto") Producto p, BindingResult bindingResult, Model model,
			@RequestParam("img") MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("Producto",p);
			return "crearpro";
		}
		// imgen
		if (p.getId_producto() == null) {
			String nombreImagen = upload.saveImage(file);
			p.setImagen(nombreImagen);

		} else {
			if (file.isEmpty()) {
				Producto pr = new Producto();
				pr = iproducto.listarId(p.getId_producto()).get();
				p.setImagen(pr.getImagen());
			} else {
				String nombreImagen = upload.saveImage(file);
				p.setImagen(nombreImagen);

			}
		}
		p.setEstadoproducto(true);// false= no registra
		model.addAttribute("message",new Message("El producto se ah actualizado correctamente.","success"));
        iproducto.guardar(p);
		return "editarpro";
	}

	@GetMapping("/bloquearproducto/{id}")
	public String bloquearPro(@PathVariable int id, Model model) {
		Producto producto = iproducto.listarId(id).get();
		producto.setEstadoproducto(false);
		model.addAttribute("message",new Message("El producto ah sido bloqueado","danger"));
		iproducto.guardar(producto);
		List<Producto> productos = iproducto.listar();
        List<Producto> productosdes = new ArrayList<>();
        List<Producto> productosbloq = new ArrayList<>();
        for (var p : productos) {
            if (p.isEstadoproducto()) {
                productosdes.add(p);
            } else {
                productosbloq.add(p);
            }
        }
        model.addAttribute("Productosbloq", productosbloq);
        model.addAttribute("Productosdes", productosdes);
		return "mantenimientopro";
	}

	@GetMapping("/desbloquearproducto/{id}")
	public String desbloquearPro(@PathVariable int id, Model model) {
		model.addAttribute("message",new Message("El producto ah sido desbloqueado","success"));
		Producto producto = iproducto.listarId(id).get();
		producto.setEstadoproducto(true);
		iproducto.guardar(producto);
		List<Producto> productos = iproducto.listar();
        List<Producto> productosdes = new ArrayList<>();
        List<Producto> productosbloq = new ArrayList<>();
        for (var p : productos) {
            if (p.isEstadoproducto()) {
                productosdes.add(p);
            } else {
                productosbloq.add(p);
            }
        }
        model.addAttribute("Productosbloq", productosbloq);
        model.addAttribute("Productosdes", productosdes);
		return "mantenimientopro";
	}

	// MANTENIMIENTO USUARIO

	// listar
	@GetMapping("/administrar/usuarios")
	public String mantenusu(Model model) {
		List<Usuario> usuarios = iusuario.listar();
		List<Usuario> us = new ArrayList<>();
		List<Usuario> usbloq = new ArrayList<>();
		for (var u : usuarios) {
			/*if (u.getId_tipousuario() == 1) {
				if (u.getEstadousuario() == true) {
					us.add(u);
				} else {
					usbloq.add(u);
				}
			}*/
		}
		model.addAttribute("Usuarios", us);
		model.addAttribute("UsuariosBloq", usbloq);
		return "mantenimientousu";
	}

	// ingresar
	@GetMapping("/administrar/usuarios/nuevo")
	public String agregarUsu(Model model) {
		model.addAttribute("Usuario", new Usuario());
		return "crearusu";
	}

	// guardar
	@PostMapping("/nuevousuario")
	public String guardarUsu(
			@Validated @ModelAttribute("Usuario") Usuario u,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("Usuario",u);
			return "crearusu";
		}
		u.setEstadousuario(true);// false= no registra
		//u.setId_tipousuario(1);
		try {
			iusuario.guardar(u);
			model.addAttribute("message",new Message("El Usuario se ah creado correctamente.","success"));
			
			Usuario userDb = iusuario.findById(u.getId_usuario());
			if(userDb!=null) {
				Rol roleDb = irol.findById(1);
				if (roleDb !=null) {
					userDb.addRole(roleDb);
					iusuario.guardar(userDb);
					System.out.println("role y user agregados correctamente");
					return "crearusu";
					
					
				}
				System.out.println("No existe el rol");
				
				System.out.println("objeto:"+irol.findById(1)+"");
				return "crearusu";
			}
			
			return "crearusu";
		
		} catch (Exception e) {
			System.out.println("" + e);
            List<Usuario> usuarios = iusuario.listar();
            boolean error = false;
            for (Usuario usuario : usuarios) {
                if (usuario.getCorreo().equals(u.getCorreo())) {
                    model.addAttribute("emailerror", new Message("El correo ya existe ", "success"));
                    System.out.println("333333333333333333333333");
                    error = true;
                }
                if (usuario.getDni().toString().equals(u.getDni().toString())) {
                    model.addAttribute("dnierror", new Message("El dni ya existe ", "success"));
                    System.out.println("11111111111111111111111");
                    error = true;
                }

            }
            if (error) {
                model.addAttribute("Usuario", u);
                System.out.println("000000000000000000000000");

            }

        }
		return "crearusu";
	}

	// editar
	@GetMapping("/administrar/usuarios/editar/{id}")
	public String editarUsu(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		model.addAttribute("Usuario", usuario);
		return "editarusu";
	}
	
	@PostMapping("/actuausuario")
	public String actualizarUsu(
			@Validated @ModelAttribute("Usuario") Usuario u,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("Usuario",u);
			return "editarusu";
			
		}
		/* atento*/
		//u.setId_tipousuario(1);
		u.setEstadousuario(true);
		try {
			iusuario.guardar(u);
			model.addAttribute("message",new Message("El Usuario se ah creado correctamente.","success"));
		} catch (Exception e) {
			Usuario usuarioEdit = iusuario.findById(u.getId_usuario());
            System.out.println("" + e);
            List<Usuario> usuarios = iusuario.listar();
            boolean error = false;
            for (Usuario usuario : usuarios) {
                if (usuarioEdit != usuario) {
                    if (usuario.getCorreo().equals(u.getCorreo())) {
                        model.addAttribute("emailerror", new Message("El correo ya existe ", "success"));
                        System.out.println("333333333333333333333333");
                        error = true;
                    }
                    if (usuario.getDni().toString().equals(u.getDni().toString())) {
                        model.addAttribute("dnierror", new Message("El dni ya existe ", "success"));
                        System.out.println("11111111111111111111111");
                        error = true;
                    }
                }

            }
            if (error) {
                model.addAttribute("Usuario", u);
                System.out.println("000000000000000000000000");

            }
			
		}
		return "editarusu";
	}

	// bloquear
	@GetMapping("/bloquearusuario/{id}")
	public String bloquearUsu(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		usuario.setEstadousuario(false);
		iusuario.guardar(usuario);
		model.addAttribute("message",new Message("El producto ah sido bloqueado","danger"));
		List<Usuario> usuarios = iusuario.listar();
		List<Usuario> us = new ArrayList<>();
		List<Usuario> usbloq = new ArrayList<>();
		for (var u : usuarios) {
			/*if (u.getId_tipousuario() == 1) {
				if (u.getEstadousuario() == true) {
					us.add(u);
				} else {
					usbloq.add(u);
				}
			}*/
		}
		model.addAttribute("Usuarios", us);
		model.addAttribute("UsuariosBloq", usbloq);
		return "mantenimientousu";
	}

	@GetMapping("/desbloquearusuario/{id}")
	public String desbloquearUsu(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		usuario.setEstadousuario(true);
		iusuario.guardar(usuario);
		model.addAttribute("message",new Message("El producto ah sido desbloqueado","success"));
		List<Usuario> usuarios = iusuario.listar();
		List<Usuario> us = new ArrayList<>();
		List<Usuario> usbloq = new ArrayList<>();
		for (var u : usuarios) {
			/*if (u.getId_tipousuario() == 1) {
				if (u.getEstadousuario() == true) {
					us.add(u);
				} else {
					usbloq.add(u);
				}
			}*/
		}
		model.addAttribute("Usuarios", us);
		model.addAttribute("UsuariosBloq", usbloq);
		return "mantenimientousu";
	}

	@GetMapping("/administrar/personal")
	public String mantenper(Model model) {
		List<Usuario> usuarios = iusuario.listar();
		List<Usuario> us = new ArrayList<>();
		List<Usuario> usbloq = new ArrayList<>();
		for (var u : usuarios) {
			/*if (u.getId_tipousuario() > 1) {
				if (u.getEstadousuario() == true) {
					us.add(u);
				} else {
					usbloq.add(u);
				}

			}*/
		}
		model.addAttribute("Personal", us);
		model.addAttribute("PersonalBloq", usbloq);
		return "mantenimientoper";
	}

	// ingresar
	@GetMapping("/administrar/personal/nuevo")
	public String agregarPer(Model model) {
		model.addAttribute("Usuario", new Usuario());
		return "crearper";
	}

	// guardar
	@PostMapping("/nuevopersonal")
	public String guardarPer(@Valid Usuario u, Model model) {
		u.setEstadousuario(true);// false= no registra
		iusuario.guardar(u);
		return "redirect:/administrar/personal";
	}

	// editar
	@GetMapping("/administrar/personal/editar/{id}")
	public String editarPer(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		model.addAttribute("usuario", usuario);
		return "editarper";
	}

	// bloquear
	@GetMapping("/bloquearpersonal/{id}")
	public String bloquearPer(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		usuario.setEstadousuario(false);
		iusuario.guardar(usuario);
		return "redirect:/administrar/personal";
	}

	@GetMapping("/desbloquearpersonal/{id}")
	public String desbloquearPer(@PathVariable int id, Model model) {
		Usuario usuario = iusuario.findById(id);
		usuario.setEstadousuario(true);
		iusuario.guardar(usuario);
		return "redirect:/administrar/personal";
	}

	// ver pedidos
	@GetMapping("/administrar/pedidos")
	public String mantenpedido(Model model) {
		List<Pedido> pedidos = ipedido.listar();
		List<Pedido> pedid = new ArrayList<>();
		List<Pedido> pedidbloq = new ArrayList<>();

		for (var pe : pedidos) {
			if (pe.isEstadopedido()) {
				pedid.add(pe);
			} else {
				pedidbloq.add(pe);
			}
		}
		model.addAttribute("Pedidos", pedid);
		model.addAttribute("PedidosBloq", pedidbloq);
		return "mantenimientopedidos";
	}

	// editar
	@GetMapping("/administrar/detalle/{id}")
	public String detallar(@PathVariable int id, Model model) {

		Optional<Pedido> pedido = ipedido.listarId(id);
		model.addAttribute("pedido", pedido);
		return "detallepedido";
	}

	// bloquear
	@GetMapping("/bloquearpedido/{id}")
	public String pedidoBloq(@PathVariable int id, Model model) {
		Pedido pedido = ipedido.listarId(id).get();
		pedido.setEstadopedido(false);
		ipedido.guardar(pedido);
		return "redirect:/administrar/pedidos";
	}

	@GetMapping("/desbloquearpedido/{id}")
	public String pedidoDesB(@PathVariable int id, Model model) {
		Pedido pedido = ipedido.listarId(id).get();
		pedido.setEstadopedido(true);
		ipedido.guardar(pedido);
		return "redirect:/administrar/pedidos";
	}

	// guardar
	@PostMapping("/guardarpedido")
	public String guardarPedid(Pedido pedido, Model model) {
		ipedido.guardar(pedido);
		return "redirect:/administrar/pedidos";
	}

	private final Logger log = LoggerFactory.getLogger(Indexcontroller.class);

	// el guardado
	@PostMapping("/search")
	public String productos(@RequestParam String nombre, Model model) {
		log.info("Nombre del producto: {}", nombre);
		List<Producto> productos = iproducto.findAll().stream().filter(p -> p.getNombre().contains(nombre))
				.collect(Collectors.toList());
		model.addAttribute("productos", productos);
		return "redirect:/administrar/productos";
	}
}
