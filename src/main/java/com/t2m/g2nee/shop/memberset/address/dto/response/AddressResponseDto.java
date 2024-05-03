import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
    private Long addressId;
    private String address;
    private String zipcode;
    private String alias;
    private String detail;
    private Boolean isDefault;
}

