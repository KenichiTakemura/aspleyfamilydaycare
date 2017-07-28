package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;

public interface ChildController {

	Parent addParent(String firstName, String lastName, String phoneNumber, String emailAddress)
			throws IOException;
	Child addChild(String firstName, String lastName, Parent parent)
			throws IOException;
}
