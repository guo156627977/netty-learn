package com.gzq.authority.guide.chapter10.http.xml;

import java.util.List;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-19 15:44.
 */
public class Customer {

    private Long customerNumber;
    private String firstName;
    private String lastName;
    private List<String> middleNames;

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }
}
