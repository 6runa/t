package steps;

import api.ApiHeaders;
import api.ApiRequest;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.cucumber.java.pt.*;
import org.json.JSONException;
import org.json.JSONObject;
import user.UsersLombok;
import utils.PropertiesUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GorestSteps extends ApiRequest {

    PropertiesUtils prop = new PropertiesUtils();
    ApiHeaders apiHeaders = new ApiHeaders();
    Faker faker = new Faker();
    UsersLombok userEnvio;

    @Dado("que possuo gorest token válido")
    public void quePossuoGorestTokenVálido() {
        token = prop.getProp("token_gorest");
    }

    @Quando("envio um request de cadastro de usuário com dados válidos")
    public void envioumrequestdecadastrodeusuáriocomdadosválidos() throws JSONException {
        super.uri = prop.getProp("uri_gorest");
        super.headers = apiHeaders.gorestHeaders(token);

        userEnvio = UsersLombok.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .gender("female")
                .status("active")
                .build();

        super.body = new JSONObject(new Gson().toJson(userEnvio));
        super.POST();
    }

    @Entao("o usuário deve ser criado corretamente")
    public void oUsuárioDeveSerCriadoCorretamente() throws JSONException {
        assertEquals(userEnvio, response.jsonPath().getObject("", UsersLombok.class),
                "Erro na comparação do objeto");
    }

    @Entao("o status code do request deve ser {int}")
    public void ostatuscodedorequestdeveser(Integer statusEsperado) {
        assertEquals(statusEsperado, response.statusCode(), "Status code diferente do esperado!");
    }

    @E("existe um usuário cadastrado na API")
    public void existeUmUsuárioCadastradoNaAPI() {
        envioumrequestdecadastrodeusuáriocomdadosválidos();
    }

    @Quando("buscar esse usuário")
    public void buscarEsseUsuário() {
        super.uri = prop.getProp("uri_gorest") + "/" + response.jsonPath().getJsonObject("id");
        super.headers = apiHeaders.gorestHeaders(token);
        super.body = new JSONObject();
        super.GET();
    }

    @Então("os dados usuários devem ser retornados")
    public void osDadosUsuáriosDevemSerRetornados() {
        assertEquals(userEnvio, response.jsonPath().getObject("", UsersLombok.class),
                "Erro na comparação do objeto");
    }


    @Quando("altero os dados do usuário")
    public void alteroOsDadosDoUsuário() {
        super.uri = prop.getProp("uri_gorest") + "/" + response.jsonPath().getJsonObject("id");
        super.headers = apiHeaders.gorestHeaders(token);
        userEnvio.setStatus("inactive");
        super.body = new JSONObject(new Gson().toJson(userEnvio));
        super.PUT();
    }

    @Então("o usuário deve ser alterado com sucesso")
    public void oUsuárioDeveSerAlteradoComSucesso() {
        assertEquals(userEnvio, response.jsonPath().getObject("", UsersLombok.class),
                "Erro na comparação do objeto");
    }

    @Quando("altero um ou mais dados do usuário")
    public void alteroUmOuMaisDadosDoUsuário() {
        super.uri = prop.getProp("uri_gorest") + "/" + response.jsonPath().getJsonObject("id");
        super.headers = apiHeaders.gorestHeaders(token);
        userEnvio.setGender("male");
        super.body = new JSONObject("{\"gender\": \"male\"}");
        super.PATCH();
    }

    @Quando("deleto esse usuário")
    public void deletoEsseUsuário() {
        super.uri = prop.getProp("uri_gorest") + "/" + response.jsonPath().getJsonObject("id");
        super.headers = apiHeaders.gorestHeaders(token);
        super.body = new JSONObject();
        super.DELETE();
    }

    @Então("o usuário é deletado corretamente")
    public void oUsuárioÉDeletadoCorretamente() {
        assertEquals("", response.asString());
    }
}
