package Vista;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Combo;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.LoginDAO;
import Modelo.Productos;
import Modelo.ProductosDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaDao;
import Modelo.login;
import Reportes.Grafico;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class Sistema extends javax.swing.JFrame {
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();
    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();
    Detalle Dv = new Detalle();
    Config conf = new Config();
    Eventos event = new Eventos();
    login lg = new login();
    LoginDAO login = new LoginDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;
    
    String hora,minutos,segundos;
    Thread hilo;
    
    public Sistema() {
        initComponents();
    }

    public Sistema(login priv) {
        initComponents();
        this.setLocationRelativeTo(null);
        Midate.setDate(fechaVenta);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdPro.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdCV.setVisible(false);
        ListarConfig();
        
        if (priv.getRol().equals("Asistente")) {
            btnProductos.setEnabled(false);
            btnProveedor.setEnabled(false);
            LabelVendedor.setText(priv.getNombre());
        } else {
            LabelVendedor.setText(priv.getNombre());
        }
        JTableHeader header = TableVenta.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
        
    }

    public void ListarCliente() {
        List<Cliente> ListarCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getDni();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);
        JTableHeader header = TableCliente.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
        JTableHeader header = TableProveedor.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);

    }

    public void ListarUsuarios() {
        List<login> Listar = login.ListarUsuarios();
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCorreo();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        TableUsuarios.setModel(modelo);
        JTableHeader header = TableUsuarios.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);
        JTableHeader header = TableProducto.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    public void ListarConfig() {
        conf = proDao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtMensaje.setText("" + conf.getMensaje());
    }

    public void ListarVentas() {
        List<Venta> ListarVenta = Vdao.Listarventas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getNombre_cli();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
        JTableHeader header = TableVentas.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 110, 255));
        header.setForeground(Color.white);
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        LabelVendedor = new javax.swing.JLabel();
        tipo = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnEliminarventa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        txtNombreClienteventa = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdCV = new javax.swing.JTextField();
        txtIdPro = new javax.swing.JTextField();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jPanel14 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        txtStockDisponible = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDirecionCliente = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNombreproveedor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        btnguardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        txtIdproducto = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtDesPro = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantPro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        btnGuardarpro = new javax.swing.JButton();
        btnEditarpro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtIdConfig = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtNombreConfig = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 110, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo_pdf.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnNuevaVenta.setBackground(new java.awt.Color(255, 102, 255));
        btnNuevaVenta.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(254, 232, 184));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaVenta.setFocusable(false);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(255, 102, 255));
        btnClientes.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(254, 232, 184));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClientes.setFocusable(false);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(255, 102, 255));
        btnProveedor.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(254, 232, 184));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedor.setFocusable(false);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(255, 102, 255));
        btnProductos.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(254, 232, 184));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProductos.setFocusable(false);
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(255, 102, 255));
        btnVentas.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(254, 232, 184));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/reporte.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.setFocusable(false);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(255, 102, 255));
        btnConfig.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(254, 232, 184));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/carrito-de-compras.png"))); // NOI18N
        btnConfig.setText("Empresa");
        btnConfig.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        LabelVendedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelVendedor.setText("Administrador");

        tipo.setForeground(new java.awt.Color(255, 255, 255));

        btnUsuarios.setBackground(new java.awt.Color(255, 102, 255));
        btnUsuarios.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(254, 232, 184));
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/agregar_usuario.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUsuarios.setFocusable(false);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(tipo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnConfig, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNuevaVenta))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelVendedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 560));

        jLabel38.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(204, 0, 204));
        jLabel38.setText("PAPELERÍA \"MAGLO\"");
        getContentPane().add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 460, -1));

        jButton1.setBackground(new java.awt.Color(255, 203, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/power_blue.png"))); // NOI18N
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 110, 130));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/fondo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 860, 130));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel2.setBackground(new java.awt.Color(255, 204, 203));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("CÓDIGO:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("NOMBRE DEL PRODUCTO");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("UNIDAD");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("PRECIO:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("DISPONIBLE");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, -1, 20));

        txtCodigoVenta.setBackground(new java.awt.Color(255, 203, 204));
        txtCodigoVenta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtCodigoVenta.setForeground(new java.awt.Color(255, 51, 51));
        txtCodigoVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaActionPerformed(evt);
            }
        });
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 150, 30));

        txtDescripcionVenta.setBackground(new java.awt.Color(255, 204, 203));
        txtDescripcionVenta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtDescripcionVenta.setForeground(new java.awt.Color(255, 51, 51));
        txtDescripcionVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDescripcionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 240, 30));

        txtCantidadVenta.setBackground(new java.awt.Color(255, 204, 203));
        txtCantidadVenta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtCantidadVenta.setForeground(new java.awt.Color(255, 51, 51));
        txtCantidadVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 100, 30));

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new java.awt.Color(255, 204, 203));
        txtPrecioVenta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtPrecioVenta.setForeground(new java.awt.Color(255, 51, 51));
        txtPrecioVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 80, 30));

        TableVenta.setBackground(new java.awt.Color(0, 153, 204));
        TableVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TableVenta.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        TableVenta.setForeground(new java.awt.Color(0, 0, 204));
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPCIÓN", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL"
            }
        ));
        TableVenta.setGridColor(new java.awt.Color(255, 0, 51));
        TableVenta.setSelectionBackground(new java.awt.Color(255, 255, 153));
        TableVenta.setSelectionForeground(new java.awt.Color(102, 255, 255));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(40);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 843, 191));

        btnEliminarventa.setBackground(new java.awt.Color(255, 204, 203));
        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/borrar.png"))); // NOI18N
        btnEliminarventa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, -1, 40));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("INE/RFC");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("NOMBRE DEL CLIENTE:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

        txtRucVenta.setBackground(new java.awt.Color(255, 204, 203));
        txtRucVenta.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtRucVenta.setForeground(new java.awt.Color(255, 51, 51));
        txtRucVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtRucVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRucVentaActionPerformed(evt);
            }
        });
        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtRucVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 116, 30));

        txtNombreClienteventa.setEditable(false);
        txtNombreClienteventa.setBackground(new java.awt.Color(255, 204, 203));
        txtNombreClienteventa.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtNombreClienteventa.setForeground(new java.awt.Color(255, 51, 51));
        txtNombreClienteventa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNombreClienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteventaActionPerformed(evt);
            }
        });
        jPanel2.add(txtNombreClienteventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 210, 30));

        btnGenerarVenta.setBackground(new java.awt.Color(255, 204, 203));
        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/impresora.png"))); // NOI18N
        btnGenerarVenta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 70, 60));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel10.setText("Total a Pagar:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, -1, -1));

        LabelTotal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTotal.setForeground(new java.awt.Color(255, 255, 51));
        LabelTotal.setText("-----");
        LabelTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 360, 70, 40));

        txtIdCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCVActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 390, 20, 10));
        jPanel2.add(txtIdPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, -1));

        btnGraficar.setBackground(new java.awt.Color(255, 204, 203));
        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/grafica.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGraficar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 60, 60));

        Midate.setBackground(new java.awt.Color(255, 153, 255));
        Midate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Midate.setForeground(new java.awt.Color(51, 204, 255));
        Midate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel2.add(Midate, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 210, 30));

        jPanel14.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, 0, 0));

        jPanel19.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 120, 10));

        jPanel20.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 250, 2));

        jPanel21.setBackground(new java.awt.Color(255, 255, 0));
        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 120, 2));

        jPanel22.setBackground(new java.awt.Color(255, 255, 0));
        jPanel22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 160, 2));

        jPanel23.setBackground(new java.awt.Color(0, 110, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtStockDisponible.setEditable(false);
        txtStockDisponible.setBackground(new java.awt.Color(255, 204, 203));
        txtStockDisponible.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtStockDisponible.setForeground(new java.awt.Color(255, 51, 51));
        txtStockDisponible.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtStockDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockDisponibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 119, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 108, 200, 0));

        jPanel24.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 405, 120, 2));

        jPanel25.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 405, 160, 2));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 153, 255));
        jLabel42.setText("FECHA");
        jPanel2.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, -1, -1));

        jTabbedPane1.addTab("1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableCliente.setBackground(new java.awt.Color(0, 153, 204));
        TableCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 0), 1, true));
        TableCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RFC/INE", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 555, 370));

        jPanel9.setBackground(new java.awt.Color(255, 102, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("RFC/INE");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtDniCliente.setBackground(new java.awt.Color(255, 102, 255));
        txtDniCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtDniCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtDniCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDniCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDniCliente.setSelectionColor(new java.awt.Color(51, 204, 255));
        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });
        jPanel9.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 160, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Nombre:");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        txtNombreCliente.setBackground(new java.awt.Color(255, 102, 255));
        txtNombreCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNombreCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombreCliente.setSelectionColor(new java.awt.Color(51, 204, 255));
        jPanel9.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 160, 30));

        txtTelefonoCliente.setBackground(new java.awt.Color(255, 102, 255));
        txtTelefonoCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTelefonoCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefonoCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTelefonoCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTelefonoCliente.setSelectionColor(new java.awt.Color(51, 204, 255));
        jPanel9.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 160, 30));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("Télefono:");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setText("Dirección:");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        txtDirecionCliente.setBackground(new java.awt.Color(255, 102, 255));
        txtDirecionCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtDirecionCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtDirecionCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDirecionCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDirecionCliente.setSelectionColor(new java.awt.Color(51, 204, 255));
        jPanel9.add(txtDirecionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 203, 160, 30));
        jPanel9.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 30, -1));

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGuardarCliente.setBorderPainted(false);
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 266, 70, 35));

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEditarCliente.setBorderPainted(false);
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 266, 70, 35));

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEliminarCliente.setBorderPainted(false);
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 317, 70, 35));

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNuevoCliente.setBorderPainted(false);
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 317, 70, 35));

        jPanel26.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 160, 2));

        jPanel27.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 160, 2));

        jPanel28.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 160, 2));

        jPanel29.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 233, 160, 2));

        jPanel46.setBackground(new java.awt.Color(0, 110, 255));

        jLabel41.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 51, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Nuevo Cliente");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 40));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 270, 370));

        jTabbedPane1.addTab("2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProveedor.setBackground(new java.awt.Color(51, 204, 255));
        TableProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TableProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TableProveedor.setForeground(new java.awt.Color(51, 153, 255));
        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableProveedor.setGridColor(new java.awt.Color(255, 204, 51));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 558, 370));

        jPanel10.setBackground(new java.awt.Color(255, 102, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 51));
        jLabel17.setText("RFC/PROV");
        jLabel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txtRucProveedor.setBackground(new java.awt.Color(255, 102, 255));
        txtRucProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtRucProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtRucProveedor.setBorder(null);
        jPanel10.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 138, 30));

        jLabel18.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 51));
        jLabel18.setText("Nombre:");
        jLabel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txtNombreproveedor.setBackground(new java.awt.Color(255, 102, 255));
        txtNombreproveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNombreproveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreproveedor.setBorder(null);
        jPanel10.add(txtNombreproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 138, 30));

        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 51));
        jLabel19.setText("Teléfono:");
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        txtTelefonoProveedor.setBackground(new java.awt.Color(255, 102, 255));
        txtTelefonoProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTelefonoProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefonoProveedor.setBorder(null);
        jPanel10.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 138, 30));

        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 51));
        jLabel20.setText("Dirección:");
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        txtDireccionProveedor.setBackground(new java.awt.Color(255, 102, 255));
        txtDireccionProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtDireccionProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccionProveedor.setBorder(null);
        jPanel10.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 138, 30));

        btnguardarProveedor.setBackground(new java.awt.Color(51, 153, 255));
        btnguardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnguardarProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnguardarProveedor.setBorderPainted(false);
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnguardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 266, 70, 35));

        btnEditarProveedor.setBackground(new java.awt.Color(51, 153, 255));
        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEditarProveedor.setBorderPainted(false);
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnEditarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 266, 70, 35));

        btnNuevoProveedor.setBackground(new java.awt.Color(51, 153, 255));
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNuevoProveedor.setBorderPainted(false);
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnNuevoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 318, 70, 35));

        btnEliminarProveedor.setBackground(new java.awt.Color(51, 153, 255));
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEliminarProveedor.setBorderPainted(false);
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 318, 70, 35));
        jPanel10.add(txtIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 24, -1));

        jPanel34.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 138, 2));

        jPanel35.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 138, 2));

        jPanel36.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 138, 2));

        jPanel37.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 138, 2));

        jPanel38.setBackground(new java.awt.Color(0, 110, 255));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Nuevo Proveedor");
        jLabel33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 50));

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 380));

        jTabbedPane1.addTab("3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(102, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProducto.setBackground(new java.awt.Color(255, 153, 255));
        TableProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TableProducto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        TableProducto.setForeground(new java.awt.Color(51, 153, 255));
        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        TableProducto.setGridColor(new java.awt.Color(0, 102, 255));
        TableProducto.setSelectionForeground(new java.awt.Color(255, 255, 102));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 590, 380));
        jPanel5.add(txtIdproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 20, -1));

        jPanel11.setBackground(new java.awt.Color(255, 153, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setBackground(new java.awt.Color(51, 255, 0));
        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 102, 255));
        jLabel22.setText("Código:");
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txtCodigoPro.setBackground(new java.awt.Color(255, 153, 255));
        txtCodigoPro.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtCodigoPro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(txtCodigoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 130, 30));

        jLabel23.setBackground(new java.awt.Color(51, 255, 0));
        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 102, 255));
        jLabel23.setText("Descripción:");
        jLabel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        txtDesPro.setBackground(new java.awt.Color(255, 153, 255));
        txtDesPro.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtDesPro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(txtDesPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 130, 30));

        jLabel24.setBackground(new java.awt.Color(51, 255, 0));
        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 102, 255));
        jLabel24.setText("Cantidad:");
        jLabel24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        txtCantPro.setBackground(new java.awt.Color(255, 153, 255));
        txtCantPro.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtCantPro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(txtCantPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 130, 30));

        jLabel25.setBackground(new java.awt.Color(51, 255, 0));
        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 102, 255));
        jLabel25.setText("Precio:");
        jLabel25.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        txtPrecioPro.setBackground(new java.awt.Color(255, 153, 255));
        txtPrecioPro.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        txtPrecioPro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });
        jPanel11.add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 130, 30));

        jLabel26.setBackground(new java.awt.Color(51, 255, 0));
        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 102, 255));
        jLabel26.setText("Proveedor:");
        jLabel26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel11.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        cbxProveedorPro.setBackground(new java.awt.Color(255, 153, 255));
        cbxProveedorPro.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cbxProveedorPro.setForeground(new java.awt.Color(255, 51, 51));
        cbxProveedorPro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbxProveedorPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProveedorProItemStateChanged(evt);
            }
        });
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });
        jPanel11.add(cbxProveedorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 130, 30));

        btnGuardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btnGuardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarproActionPerformed(evt);
            }
        });
        jPanel11.add(btnGuardarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, 35));

        btnEditarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnEditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarproActionPerformed(evt);
            }
        });
        jPanel11.add(btnEditarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, 35));

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });
        jPanel11.add(btnEliminarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, -1, 35));

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });
        jPanel11.add(btnNuevoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, -1, 35));

        jPanel30.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 130, 2));

        jPanel31.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 130, 2));

        jPanel32.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 130, 2));

        jPanel33.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 130, 2));

        jPanel39.setBackground(new java.awt.Color(0, 110, 255));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Nuevo Producto");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 50));

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 380));

        jTabbedPane1.addTab("4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(102, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas.setBackground(new java.awt.Color(255, 51, 255));
        TableVentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TableVentas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TableVentas.setForeground(new java.awt.Color(255, 153, 255));
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        TableVentas.setGridColor(new java.awt.Color(255, 153, 0));
        TableVentas.setSelectionForeground(new java.awt.Color(255, 255, 102));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 766, 310));

        btnPdfVentas.setBackground(new java.awt.Color(102, 204, 255));
        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdfico.png"))); // NOI18N
        btnPdfVentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPdfVentas.setBorderPainted(false);
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 46, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 204, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("REGISTRO DE VENTAS");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 360, 60));

        jTabbedPane1.addTab("5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(102, 204, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel7.setForeground(new java.awt.Color(102, 204, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 255));
        jLabel32.setText("DATOS DE LA EMPRESA");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 153, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(txtIdConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 24, -1));

        jLabel30.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 0));
        jLabel30.setText("Dirección");
        jPanel8.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jLabel29.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 51, 0));
        jLabel29.setText("Teléfono");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        txtTelefonoConfig.setBackground(new java.awt.Color(255, 153, 255));
        txtTelefonoConfig.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTelefonoConfig.setForeground(new java.awt.Color(0, 255, 255));
        txtTelefonoConfig.setBorder(null);
        jPanel8.add(txtTelefonoConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 218, 30));

        txtDireccionConfig.setBackground(new java.awt.Color(255, 153, 255));
        txtDireccionConfig.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDireccionConfig.setForeground(new java.awt.Color(0, 255, 255));
        txtDireccionConfig.setBorder(null);
        jPanel8.add(txtDireccionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 147, 30));

        jLabel31.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 51, 0));
        jLabel31.setText("Mensaje");
        jPanel8.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        txtMensaje.setBackground(new java.awt.Color(255, 153, 255));
        txtMensaje.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtMensaje.setForeground(new java.awt.Color(0, 255, 255));
        txtMensaje.setBorder(null);
        jPanel8.add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 400, 30));

        btnActualizarConfig.setBackground(new java.awt.Color(255, 255, 51));
        btnActualizarConfig.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnActualizarConfig.setForeground(new java.awt.Color(255, 0, 51));
        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnActualizarConfig.setFocusable(false);
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });
        jPanel8.add(btnActualizarConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 130, 35));

        jLabel27.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 51, 0));
        jLabel27.setText("RFC/EMPRESA");
        jPanel8.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtRucConfig.setBackground(new java.awt.Color(255, 153, 255));
        txtRucConfig.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtRucConfig.setForeground(new java.awt.Color(0, 255, 255));
        txtRucConfig.setBorder(null);
        jPanel8.add(txtRucConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 147, 30));

        jLabel28.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 51, 0));
        jLabel28.setText("Nombre");
        jPanel8.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, -1, -1));

        txtNombreConfig.setBackground(new java.awt.Color(255, 153, 255));
        txtNombreConfig.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNombreConfig.setForeground(new java.awt.Color(0, 255, 255));
        txtNombreConfig.setBorder(null);
        jPanel8.add(txtNombreConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 220, 30));

        jPanel41.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 147, 2));

        jPanel42.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 147, 2));

        jPanel43.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 400, 2));

        jPanel44.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 220, 2));

        jPanel45.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 220, 2));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 420, 340));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/empresa.png"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 410, 290));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 10));

        jTabbedPane1.addTab("6", jPanel7);

        jPanel12.setBackground(new java.awt.Color(102, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(204, 102, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setBackground(new java.awt.Color(204, 102, 255));
        jLabel34.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 255, 255));
        jLabel34.setText("Correo Electrónico");
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jLabel35.setBackground(new java.awt.Color(204, 102, 255));
        jLabel35.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 255, 255));
        jLabel35.setText("Password");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        txtCorreo.setBackground(new java.awt.Color(204, 102, 255));
        txtCorreo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(0, 102, 255));
        txtCorreo.setBorder(null);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel13.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 126, 226, 30));

        txtPass.setBackground(new java.awt.Color(204, 102, 255));
        txtPass.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtPass.setForeground(new java.awt.Color(0, 102, 255));
        txtPass.setBorder(null);
        jPanel13.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 182, 226, 30));

        btnIniciar.setBackground(new java.awt.Color(204, 102, 255));
        btnIniciar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(0, 102, 255));
        btnIniciar.setText("Registrar");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        jPanel13.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 341, 226, 34));

        jLabel36.setBackground(new java.awt.Color(204, 102, 255));
        jLabel36.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 255, 255));
        jLabel36.setText("Nombre:");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        txtNombre.setBackground(new java.awt.Color(204, 102, 255));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(0, 102, 255));
        txtNombre.setBorder(null);
        jPanel13.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 243, 226, 30));

        jLabel37.setBackground(new java.awt.Color(204, 102, 255));
        jLabel37.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 255, 255));
        jLabel37.setText("Rol:");
        jPanel13.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, -1, -1));

        cbxRol.setBackground(new java.awt.Color(204, 102, 255));
        cbxRol.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        cbxRol.setForeground(new java.awt.Color(0, 102, 255));
        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));
        jPanel13.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 304, 226, 30));

        jPanel15.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 156, 226, 2));

        jPanel16.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 212, 226, 2));

        jPanel17.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 273, 226, 2));

        jPanel18.setBackground(new java.awt.Color(255, 102, 255));

        jLabel39.setBackground(new java.awt.Color(204, 255, 51));
        jLabel39.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("REGISTRO DE USUARIOS");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel39)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 60));

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 280, 380));

        TableUsuarios.setBackground(new java.awt.Color(255, 153, 255));
        TableUsuarios.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TableUsuarios.setForeground(new java.awt.Color(0, 153, 255));
        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICACION", "NOMBRE", "CORREO", "POSICION"
            }
        ));
        TableUsuarios.setGridColor(new java.awt.Color(255, 255, 51));
        TableUsuarios.setRowHeight(20);
        jScrollPane6.setViewportView(TableUsuarios);

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 540, 380));

        jTabbedPane1.addTab("7", jPanel12);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 95, 860, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        LimpiarProveedor();
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
        btnEditarpro.setEnabled(false);
        btnEliminarPro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
        LimpiarProductos();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
        LimpiarTable();
        ListarUsuarios();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        // TODO add your handling code here:
        cbxProveedorPro.removeAllItems();
        llenarProveedor();

    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtPass.getPassword().equals("")) {
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        } else {
            String correo = txtCorreo.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtNombre.getText();
            String rol = cbxRol.getSelectedItem().toString();
            lg.setNombre(nom);
            lg.setCorreo(correo);
            lg.setPass(pass);
            lg.setRol(rol);
            login.Registrar(lg);
            LimpiarTable();
            ListarUsuarios();
            JOptionPane.showMessageDialog(null, "Usuario Registrado");
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            conf.setRuc(txtRucConfig.getText());
            conf.setNombre(txtNombreConfig.getText());
            conf.setTelefono(txtTelefonoConfig.getText());
            conf.setDireccion(txtDireccionConfig.getText());
            conf.setMensaje(txtMensaje.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed

        if (txtIdVenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            v = Vdao.BuscarVenta(Integer.parseInt(txtIdVenta.getText()));
            Vdao.pdfV(v.getId(), v.getCliente(), v.getTotal(), v.getVendedor());
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        // TODO add your handling code here:
        LimpiarProductos();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText());
                proDao.EliminarProductos(id);
                LimpiarTable();
                LimpiarProductos();
                ListarProductos();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnEditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarproActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioPro.getText())) {
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDesPro.getText());
                Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                pro.setProveedor(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
                pro.setId(Integer.parseInt(txtIdproducto.getText()));
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
                cbxProveedorPro.removeAllItems();
                llenarProveedor();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarproActionPerformed

    private void btnGuardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarproActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(cbxProveedorPro.getSelectedItem()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioPro.getText())) {
            pro.setCodigo(txtCodigoPro.getText());
            pro.setNombre(txtDesPro.getText());
            Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
            pro.setProveedor(itemP.getId());
            pro.setStock(Integer.parseInt(txtCantPro.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            proDao.RegistrarProductos(pro);
            JOptionPane.showMessageDialog(null, "Productos Registrado");
            LimpiarTable();
            ListarProductos();
            LimpiarProductos();
            cbxProveedorPro.removeAllItems();
            llenarProveedor();
            btnEditarpro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            btnGuardarpro.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarproActionPerformed

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void cbxProveedorProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProveedorProItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cbxProveedorProItemStateChanged

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        // TODO add your handling code here:
        btnEditarpro.setEnabled(true);
        btnEliminarPro.setEnabled(true);
        btnGuardarpro.setEnabled(true);
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TableProducto.getValueAt(fila, 0).toString());
        pro = proDao.BuscarId(Integer.parseInt(txtIdproducto.getText()));
        txtCodigoPro.setText(pro.getCodigo());
        txtDesPro.setText(pro.getNombre());
        txtCantPro.setText("" + pro.getStock());
        txtPrecioPro.setText("" + pro.getPrecio());
        cbxProveedorPro.setSelectedItem(new Combo(pro.getProveedor(), pro.getProveedorPro()));
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnguardarProveedor.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
                pr.setRuc(txtRucProveedor.getText());
                pr.setNombre(txtNombreproveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
            pr.setRuc(txtRucProveedor.getText());
            pr.setNombre(txtNombreproveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            PrDao.RegistrarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            LimpiarTable();
            ListarProveedor();
            LimpiarProveedor();
            btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnguardarProveedor.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
        }
    }//GEN-LAST:event_btnguardarProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        // TODO add your handling code here:
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreproveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        // TODO add your handling code here:
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDirecionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        // TODO add your handling code here:

        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Grafico.Graficar(fechaReporte);

    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteventa.getText())) {
                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                LimpiarTableVenta();
                LimpiarClienteventa();
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Noy productos en la venta");
        }

    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRucVenta.getText())) {
                int dni = Integer.parseInt(txtRucVenta.getText());
                cl = client.Buscarcliente(dni);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed

         if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteventa.getText())) {
                modelo = (DefaultTableModel) TableVenta.getModel();
                modelo.removeRow(TableVenta.getSelectedRow());
                TotalPagar();
                txtCodigoVenta.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "No hay elementos en la tabla");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al tratar de eliminar");
        }
    }//GEN-LAST:event_btnEliminarventaActionPerformed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                int id = Integer.parseInt(txtIdPro.getText());
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableVenta.getModel();
                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(id);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    TableVenta.setModel(tmp);
                    TotalPagar();
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    txtIdPro.setText("" + pro.getId());
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockDisponible.setText("" + pro.getStock());
                    txtCantidadVenta.requestFocus();
                } else {
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDirecionCliente.getText())) {
            cl.setDni(txtDniCliente.getText());
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDirecionCliente.getText());
            client.RegistrarCliente(cl);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            LimpiarTable();
            LimpiarCliente();
            ListarCliente();
            btnEditarCliente.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnGuardarCliente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText())) {
                cl.setDni(txtDniCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDirecionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                client.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void txtIdCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCVActionPerformed

    private void txtStockDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockDisponibleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNombreClienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteventaActionPerformed

    private void txtRucVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRucVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucVentaActionPerformed

    private void txtCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelVendedor;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEditarpro;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarpro;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnguardarProveedor;
    private javax.swing.JComboBox<Object> cbxProveedorPro;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDesPro;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDirecionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreproveedor;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDirecionCliente.setText("");
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreproveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdPro.setText("");
        txtCodigoPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPrecioPro.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimparVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdCV.getText());
        String vendedor = LabelVendedor.getText();
        double monto = Totalpagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);

        }
        int cliente = Integer.parseInt(txtIdCV.getText());
        Vdao.pdfV(id, cliente, Totalpagar, LabelVendedor.getText());
    }

    private void ActualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarId(id);
            int StockActual = pro.getStock() - cant;
            Vdao.ActualizarStock(StockActual, id);

        }
    }

    private void LimpiarTableVenta() {
        tmp = (DefaultTableModel) TableVenta.getModel();
        int fila = TableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarClienteventa() {
        txtRucVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdCV.setText("");
    }

    private void llenarProveedor() {
        List<Proveedor> lista = PrDao.ListarProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new Combo(id, nombre));
        }
    }
}
