/**
 * Módulo principal de la aplicación Dragones y Mazmorras.
 * 
 * Este módulo define los paquetes requeridos para ejecutar la aplicación,
 * incluyendo JavaFX para la interfaz gráfica y JSON.simple para el manejo de archivos JSON.
 */
module com.mazmorras {

    /**
     * Requiere el módulo de controles JavaFX (botones, listas, etc.).
     */
    requires javafx.controls;

    /**
     * Requiere el módulo FXML de JavaFX para cargar archivos FXML (interfaces gráficas).
     */
    requires javafx.fxml;

    /**
     * Requiere la biblioteca JSON.simple para la lectura y escritura de archivos JSON.
     */
    requires json.simple;

    /**
     * Requiere el módulo gráfico de JavaFX, necesario para crear escenas y gráficos.
     */
    requires javafx.graphics;

    /**
     * Abre el paquete de controladores a JavaFX para que pueda inyectar dependencias desde FXML.
     */
    opens com.mazmorras.controllers to javafx.fxml;

    /**
     * Exporta el paquete principal para que sea accesible desde fuera del módulo.
     */
    exports com.mazmorras;
}
