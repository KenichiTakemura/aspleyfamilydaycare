package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;

public interface ChildController {
	
	Parent addParent(String firstName, String lastName) throws IOException;

	Parent addParent(String firstName, String lastName, String phoneNumber, String emailAddress) throws IOException;

	Parent updateParent(Parent parent) throws IOException;

	Child addChild(String firstName, String lastName, String childNumber, Parent parent) throws IOException;

	Child updateChild(Child child) throws IOException;

	Parent findParent(String id) throws IOException;

	Child findChild(String id) throws IOException;

}
