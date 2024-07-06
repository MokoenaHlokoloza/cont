package mtn.momo.contract.repayment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mtn.momo.contract.repayment.model.UserDto;
import mtn.momo.contract.repayment.model.response.RegisterUserResponseWrapper;
import mtn.momo.contract.repayment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserServiceControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserServiceController controller = new UserServiceController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");

        RegisterUserResponseWrapper responseWrapper = new RegisterUserResponseWrapper();
        responseWrapper.setData(userDto);

        when(userService.registerNewUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/user/register/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.username").value("testUser"));
    }

    @Test
    public void testRegisterUser_Failure() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");

        when(userService.registerNewUser(any(UserDto.class))).thenThrow(new RuntimeException("Failed to register"));

        mockMvc.perform(post("/api/user/register/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0].message").value("Failed to register"));
    }
}
