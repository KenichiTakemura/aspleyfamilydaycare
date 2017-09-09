package com.ktiteng.controller.service;

import java.io.IOException;
import java.util.Collection;

import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;

public interface ChildController {

	Parent addParent(Parent parent) throws IOException;

	Parent updateParent(Parent parent) throws IOException;

	Child addChild(Child child) throws IOException;

	Child updateChild(Child child) throws IOException;

	Collection<Parent> getAllParents() throws IOException;

	Parent findParent(String id) throws IOException;

	Parent findParent(String firstName, String lastName) throws IOException;

	Collection<Child> getAllChildren() throws IOException;

	Child findChild(String id) throws IOException;

	Child findChild(String firstName, String lastName) throws IOException;

}
