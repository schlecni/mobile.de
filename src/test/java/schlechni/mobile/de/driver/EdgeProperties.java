package schlechni.mobile.de.driver;

import lombok.Getter;
import org.openqa.selenium.remote.BrowserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConditionalOnProperty(name = "driver.name", havingValue = BrowserType.EDGE)
public class EdgeProperties implements WebDriverProperties {

    @Value("${driver.name}")
    private String driverName;

    @Value("${driver.edge.key}")
    private String driverKey;

    @Value("${driver.edge.path}")
    private String driverPath;

    @Value("${driver.url}")
    private String url;

}
