# 🍻 **BarVendor** - **Aplicación Web y Backend**  

---

## 📌 **¿Qué es BarVendor?**  
BarVendor es una solución integral para la gestión de bares y restaurantes, diseñada para optimizar las actividades y el flujo de trabajo mediante tecnología avanzada. Este sistema conecta eficientemente a los roles clave del negocio, permitiendo una coordinación fluida y mejorando la experiencia tanto de los empleados como de los clientes.  

### 🎯 **Objetivos principales:**  
1. **Conexión integral:** Facilitar la interacción entre **camareros**, **cocineros** y **administradores/dueños**.  
2. **Registro eficiente:** Gestionar y registrar información sobre pedidos, cuentas, mesas, productos y usuarios.  
3. **Digitalización de procesos:** Automatizar y simplificar las tareas diarias mediante tecnología, mejorando la eficacia operativa.  

---

## 💻 **Tecnologías empleadas:**  
- **Base de datos:** MySQL  
- **Backend Framework:** Spring Boot  
- **Frontend Web:** Angular con CSS y Bootstrap  
- **Frontend Móvil:** Ionic con Angular  
- **Servidor Web:** Heroku  
- **Navegadores compatibles:** Chrome, Firefox, Opera  
- **Entornos de desarrollo:** Visual Studio Code, Android Studio  
- **Gestores de dependencias:** Maven, npm  
- **Control de versiones:** GitHub  

---

## 🖥️ **Aplicación Web - Backend**  
La **App Web**, desarrollada con **Spring Boot**, actúa como el núcleo del sistema, permitiendo a los administradores y cocineros gestionar las operaciones del bar de manera eficiente.  

### 🔑 **Funcionalidades principales del Backend:**  
1. **Gestión de usuarios y roles:**  
   - Creación, modificación y eliminación de usuarios.  
   - Asignación de roles específicos: **Camarero**, **Cocinero**, **Administrador**.  

2. **Gestión de productos:**  
   - CRUD (Crear, Leer, Actualizar, Eliminar) de productos.  
   - Habilitación y deshabilitación de productos según disponibilidad.  

3. **Gestión de pedidos:**  
   - Registro y seguimiento de pedidos realizados por los camareros.  
   - Actualización del estado del pedido (en marcha/listo).  

4. **Gestión de cuentas y mesas:**  
   - Asignación de pedidos a cuentas activas por mesa.  
   - Actualización automática del estado de las mesas (ocupada/libre).  

5. **Estadísticas y reportes:**  
   - Generación de informes de productos más vendidos.  
   - Análisis de cuentas y pedidos para estrategias de marketing.  

6. **Descuentos globales:**  
   - Aplicación y eliminación de descuentos temporales en todos los productos.  

### ⚙️ **Arquitectura del Backend:**  
1. **Controladores:** Manejan las solicitudes HTTP desde el frontend y gestionan las rutas principales del sistema.  
2. **Servicios:** Implementan la lógica de negocio para cada funcionalidad del sistema.  
3. **Repositorios:** Se encargan de la comunicación directa con la base de datos MySQL.  
4. **Entidades:** Representan los modelos de datos principales, como `Usuario`, `Producto`, `Pedido`, `Cuenta`, y `Mesa`.  
5. **Seguridad:**  
   - Implementación de autenticación y autorización mediante **Spring Security**.  
   - Uso de JWT (JSON Web Tokens) para proteger las API.  

---

## 🌟 **Integración con el Sistema Completo:**  
- **Frontend Web:** Conecta directamente con el backend a través de servicios REST.  
- **App Móvil:** Se comunica con el backend para gestionar pedidos, cuentas y usuarios.  
- **Base de datos:** MySQL almacena toda la información estructurada y permite consultas eficientes.  

---

## 📑 **Recursos adicionales**  
- 📄 **Presentación del proyecto:**  
  [Presentación PDF](https://docs.google.com/presentation/d/1FoXfac5rst8z3PSUA7DmonkYFChOw8TdanJqfQ5rUAk/edit?usp=sharing)  

- 📘 **Manual de usuario:**  
  [Manual en Google Docs](https://docs.google.com/document/d/13sCFEjfv-gxSF0XIWPsmT8FvVD2L6HQ1JoB8-JOINyQ/edit?usp=sharing)  

---

## 🏁 **Conclusión**  
La **Aplicación Web** de BarVendor, respaldada por un robusto backend desarrollado en **Spring Boot**, ofrece una plataforma poderosa para la gestión integral de bares y restaurantes. Desde la administración de productos y usuarios hasta la coordinación de pedidos y cuentas, el sistema está diseñado para garantizar la máxima eficiencia operativa y una experiencia superior para todos los involucrados.  
