package com.example.demo.service;

import com.example.demo.models.Client;
import com.example.demo.models.Seller;
import com.example.demo.models.enums.Status;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SellerRepository;
import com.example.demo.requests.AuthenticationRequest;
import com.example.demo.responses.AuthenticationResponse;
import com.example.demo.requests.RegisterRequest;
import com.example.demo.models.enums.Role;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SellerRepository sellerRepository;

    private final ClientRepository clientRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println("Начинается регистрация");

        if(userRepository.findAllByEmail(request.getEmail()).size() != 0){
            System.out.println("Пользователь уже зарегистрирован");
            return null;
        }
        System.out.println(request.getX());
        System.out.println(request.getY());


        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();


        if (user.getRole().equals(Role.USER)) {
            user.setStatus(Status.ACTIVATED);

            Client client = Client.builder()
                    .user(user)
                    .xCoordinate(request.getX())
                    .yCoordinate(request.getY())
                    .build();

            userRepository.save(user);

            clientRepository.save(client);
        }

        if (user.getRole().equals(Role.ADMIN)) {
            user.setStatus(Status.NO_ACTIVATED);
            userRepository.save(user);
            System.out.println("id USER = " + user.getId());

            Seller seller = Seller.builder()
                    .user(user).
                    build();

            sellerRepository.save(seller);

            System.out.println("Сохраняем админа продавца неактивированного");
            return null;
        }


        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("Нашли в репозитории");

        if (!request.getRole().equals(user.getRole().toString())) {
            System.out.println(request.getRole() + " " + user.getRole());
            System.out.println("Ошибка несоответствия роли");
            return null;
        }
        System.out.println("Проверили роль");

        if (user.getStatus().equals(Status.NO_ACTIVATED)) {
            System.out.println(user.getStatus());
            System.out.println("Ошибка несоответствия статуса");
            return null;
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
