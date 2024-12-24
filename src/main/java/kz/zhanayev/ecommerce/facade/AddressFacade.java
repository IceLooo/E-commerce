package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.AddressDTO;
import kz.zhanayev.ecommerce.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressFacade {

    public Address dtoToEntity(AddressDTO addressDTO) {
        return mapToEntity(new Address(), addressDTO);
    }

    public AddressDTO entityToDTO(Address address) {
        return mapToDTO(new AddressDTO(), address);
    }

    private Address mapToEntity(Address address, AddressDTO addressDTO) {
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setRegion(addressDTO.getRegion());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setEntrance(addressDTO.getEntrance());
        address.setApartmentOffice(addressDTO.getApartmentOffice());
        address.setCourierComments(addressDTO.getCourierComments());
        return address;
    }

    private AddressDTO mapToDTO(AddressDTO addressDTO, Address address) {
        addressDTO.setId(address.getId());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCity(address.getCity());
        addressDTO.setRegion(address.getRegion());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setEntrance(address.getEntrance());
        addressDTO.setApartmentOffice(address.getApartmentOffice());
        addressDTO.setCourierComments(address.getCourierComments());
        return addressDTO;
    }
}
