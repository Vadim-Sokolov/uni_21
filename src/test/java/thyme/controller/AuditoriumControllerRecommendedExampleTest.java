package thyme.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import thyme.controller.thyme.AuditoriumControllerThyme;
import thyme.service.AuditoriumService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;

@WebMvcTest(AuditoriumControllerThyme.class)
class AuditoriumControllerRecommendedExampleTest {

    @MockBean
    private AuditoriumService service;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAuditoriumsTest() throws Exception {
        this.mockMvc.perform(get("/auditoriums"))
                .andExpect(status().isOk())
                .andExpect(view().name("auditorium/auditoriums"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void getAuditoriumsContainStringTest() throws Exception {
        this.mockMvc.perform(get("/auditoriums"))
        	.andExpect(status().isOk())
            .andExpect(content().string("<!DOCTYPE html>\r\n" + 
            		"<html lang=\"en\">\r\n" + 
            		"\r\n" + 
            		"<head>\r\n" + 
            		"    <meta charset=\"UTF-8\" />\r\n" + 
            		"    <title>University</title>\r\n" + 
            		"    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\r\n" + 
            		"        integrity=\"sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6\" crossorigin=\"anonymous\">\r\n" + 
            		"    <link rel=\"stylesheet\" href=\"style.css\">\r\n" + 
            		"</head>\r\n" + 
            		"\r\n" + 
            		"<body>\r\n" + 
            		"\r\n" + 
            		"    <table class=\"table table-primary table-bordered border-dark\">\r\n" + 
            		"        <caption>University</caption>\r\n" + 
            		"        <tbody>\r\n" + 
            		"            <tr>\r\n" + 
            		"                <td><a href=\"/\">Home</a></td>\r\n" + 
            		"                <td><a href=\"auditoriums\">Auditoriums</a></td>\r\n" + 
            		"                <td><a href=\"courses\">Courses</a></td>\r\n" + 
            		"                <td><a href=\"facultys\">Faculties</a></td>\r\n" + 
            		"                <td><a href=\"groups\">Groups</a></td>\r\n" + 
            		"                <td><a href=\"lectures\">Lectures</a></td>\r\n" + 
            		"                <td><a href=\"students\">Students</a></td>\r\n" + 
            		"                <td><a href=\"teachers\">Teachers</a></td>\r\n" + 
            		"            </tr>\r\n" + 
            		"        </tbody>\r\n" + 
            		"    </table>\r\n" + 
            		"\r\n" + 
            		"    <div class=\"container my-2\">\r\n" + 
            		"        <a href=\"/auditoriums/newForm\" class=\"btn btn-primary btn-sm mb-3\">Add New Auditorium</a>\r\n" + 
            		"        <table class=\"table table-primary table-bordered border-dark table-striped table-responsive-md\">\r\n" + 
            		"            <caption>Auditoriums</caption>\r\n" + 
            		"            <thead>\r\n" + 
            		"                <tr>\r\n" + 
            		"                    <th scope=\"col\">Id</th>\r\n" + 
            		"                    <th scope=\"col\">Name</th>\r\n" + 
            		"                    <th scope=\"col\">Capacity</th>\r\n" + 
            		"                    <th scope=\"col\">Actions</th>\r\n" + 
            		"                </tr>\r\n" + 
            		"            </thead>\r\n" + 
            		"            <tbody>\r\n" + 
            		"                \r\n" + 
            		"            </tbody>\r\n" + 
            		"        </table>\r\n" + 
            		"    </div>\r\n" + 
            		"</body>\r\n" + 
            		"\r\n" + 
            		"</html>"));
    }
}