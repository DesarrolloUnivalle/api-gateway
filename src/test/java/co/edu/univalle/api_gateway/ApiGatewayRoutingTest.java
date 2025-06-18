package co.edu.univalle.api_gateway;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApiGatewayRoutingTest {

    private static WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();

        configureFor("localhost", 8081);

        stubFor(get(urlEqualTo("/usuarios/test"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"mensaje\": \"ok\"}")
                        .withStatus(200)));
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void testUsuariosServiceRoute() {
        WebTestClient webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        webTestClient.get()
                .uri("/usuarios/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println) // Útil para debugging
                .jsonPath("$.mensaje").isEqualTo("ok");
    }
}
