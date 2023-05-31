package com.inspire.togglz2;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;

public enum myFeatures implements Feature {

    @Label("Apply discounts to products or not") //Discount_applied feature toggle, below comment was for testing out Activation Strategies for Togglz
   /* @DefaultActivationStrategy(id = SystemPropertyActivationStrategy.ID,
            parameters = {
                    @ActivationParameter(
                            name = SystemPropertyActivationStrategy.PARAM_PROPERTY_NAME,
                            value = "discount.feature"),
                    @ActivationParameter(
                            name = SystemPropertyActivationStrategy.PARAM_PROPERTY_VALUE,
                            value = "true") })*/
            DISCOUNT_APPLIED;
}
