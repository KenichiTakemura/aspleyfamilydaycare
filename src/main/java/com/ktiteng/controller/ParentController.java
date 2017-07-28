package com.ktiteng.controller;

import java.io.IOException;

public interface ParentController {

	void addParent(String firstName, String lastName, String phoneNumber, String emailAddress) throws IOException;
}
