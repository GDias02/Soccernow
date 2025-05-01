package pt.ul.fc.css.soccernow.handlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.JogoMapper;
import pt.ul.fc.css.soccernow.repositories.CartaoRepository;
import pt.ul.fc.css.soccernow.repositories.GoloRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

@Service
public class JogoHandler implements IJogoHandler {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private GoloRepository goloRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public JogoDto getJogo(long id) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(id);
        if (jogoOptional.isEmpty()) {
            return null;
        }
        return JogoMapper.jogoToDto(jogoOptional.get());
    }

    public JogoDto saveJogo(Jogo jogo) {
        return JogoMapper.jogoToDto(jogoRepository.save(jogo));
    }

    public JogoDto createJogo(JogoDto jogodto) {
        Jogo jogo = JogoMapper.dtoToJogo(jogodto);

        Jogo savedJogo = jogoRepository.save(jogo);
        jogodto.setId(savedJogo.getId());
        return jogodto;
    }

    public JogoDto updateJogo(long jogoId, JogoDto jogodto) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(jogoId);
        Jogo updatedJogo;
        if (jogoOpt.isEmpty()) {
            return null;
        } else {
            updatedJogo = jogoOpt.get();
        }
        if (updateValido(updatedJogo, jogodto)) {
            EstatisticaJogo updatedStats = EstatisticaMapper.dtoToEstatisticaJogo(jogodto.getStats());
            JogoMapper.dtoToJogo(jogodto, updatedJogo);
            goloRepository.saveAll(updatedStats.getGolos());
            cartaoRepository.saveAll(updatedStats.getCartoes());
            jogoRepository.save(updatedJogo);
        }
        return JogoMapper.jogoToDto(updatedJogo);
    }

    //PERMITE DEMASIADAS COISAS
    private boolean updateValido(Jogo updatedJogo, JogoDto jogodto) {
        return (updatedJogo.getEstadoAtual() != EstadoDeJogo.TERMINADO);
    //permite o registo final de um jogo.
    }

}
/*
 * @Service
 * public class CustomerHandler {
 * 
 * @Autowired
 * private CustomerRepository customerRepository;
 * 
 * public List<CustomerDto> getAllCustomers() {
 * List<Customer> customers = customerRepository.findAll();
 * return customers.stream()
 * .map(this::mapToDto)
 * .toList();
 * }
 * 
 * public CustomerDto saveCustomer(Customer customer) {
 * return mapToDto(customerRepository.save(customer));
 * }
 * 
 * public CustomerDto getCustomerByVat(String vat) {
 * Optional<Customer> customerOptional =
 * customerRepository.findByVatNumber(vat);
 * if (customerOptional.isEmpty()) {
 * return null;
 * }
 * return mapToDto(customerOptional.get());
 * }
 * 
 * public CustomerDto mapToDto(Customer customer) {
 * CustomerDto customerDto = new CustomerDto();
 * customerDto.setId(customer.getId());
 * customerDto.setVatNumber(customer.getVatNumber());
 * customerDto.setDesignation(customer.getDesignation());
 * customerDto.setPhoneNumber(customer.getPhoneNumber());
 * return customerDto;
 * }
 * 
 * 
 * //TODO Its missing a lot of checks and exception handling. Will add at
 * another time
 * public CustomerDto createCustomer(CustomerDto customerDto) {
 * Customer customer = new Customer();
 * customer.setVatNumber(customerDto.getVatNumber());
 * customer.setDesignation(customerDto.getDesignation());
 * customer.setPhoneNumber(customerDto.getPhoneNumber());
 * 
 * Customer savedCustomer = customerRepository.save(customer);
 * 
 * customerDto.setId(savedCustomer.getId());
 * return customerDto;
 * }
 * }
 */
