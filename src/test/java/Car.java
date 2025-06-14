import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Car {
    private String manufacturer;
    private Integer year;
    private String model;
    private String color;
}
