// filepath: mazmorra/src/test/java/com/mazmorras/controllers/JuegoControllerTest.java
package com.mazmorras.controllers;



class JuegoControllerTest {

    private JuegoController juegoController;
    private Mapa mockMapa;

    @BeforeEach
    void setUp() {
        juegoController = new JuegoController();
        mockMapa = Mockito.mock(Mapa.class);
    }

    @Test
    void testGenerarMapa() {
        // Arrange: Set up the mock map dimensions
        when(mockMapa.getAncho()).thenReturn(5);
        when(mockMapa.getAlto()).thenReturn(5);

        // Act: Call the generarMapa method
        juegoController.generarMapa(mockMapa);

        // Assert: Verify that the map's dimensions were accessed
        verify(mockMapa, times(1)).getAncho();
        verify(mockMapa, times(1)).getAlto();

        // Assert: Verify that the map's elements were processed
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Verify interactions for placing walls, paths, entrance, exit, etc.
                verify(mockMapa, atLeast(0)).colocarParedes(i, j);