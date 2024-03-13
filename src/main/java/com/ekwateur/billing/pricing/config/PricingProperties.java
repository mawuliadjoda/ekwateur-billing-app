package com.ekwateur.billing.pricing.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "pricing")
public class PricingProperties {

    private Electricity electricity;
    private Gaz gaz;

    @Getter
    @Setter
    public static class Electricity {
        private double particular;
        private Professional professional;

        @Getter
        @Setter
        public static class Professional {
            private double gt1M;
            private double lt1M;
        }
    }

    @Getter
    @Setter
    public static class Gaz {
        private double particular;
        private Professional professional;

        @Getter
        @Setter
        public static class Professional {
            private double gt1M;
            private double lt1M;
        }
    }
}
