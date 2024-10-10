package ra.md4.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormChangeQuantity {
    private Integer cartItemId;
    private int newQuantity;
}
