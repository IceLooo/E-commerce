package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.AddressDTO;
import kz.zhanayev.ecommerce.models.Address;
import kz.zhanayev.ecommerce.models.User;

public class AddressMapper {
    public static AddressDTO toDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
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

    public static Address toEntity(AddressDTO addressDTO, User user) {
        Address address = new Address();
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setRegion(addressDTO.getRegion());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setEntrance(addressDTO.getEntrance());
        address.setApartmentOffice(addressDTO.getApartmentOffice());
        address.setCourierComments(addressDTO.getCourierComments());
        address.setUser(user);
        return address;
    }
}
