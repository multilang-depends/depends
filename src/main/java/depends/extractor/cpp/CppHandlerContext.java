/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.extractor.cpp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionEntity;
import depends.entity.FunctionEntityProto;
import depends.entity.FunctionEntityImpl;
import depends.entity.GenericName;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.HandlerContext;
import depends.relations.Inferer;

public class CppHandlerContext extends HandlerContext {

	public CppHandlerContext(EntityRepo entityRepo,Inferer inferer) {
		super(entityRepo,inferer);
	}

	public Entity foundNamespace(String nampespaceName) {
		PackageEntity pkgEntity = new PackageEntity(nampespaceName, currentFile(),idGenerator.generateId());
		entityRepo.add(pkgEntity);
		entityStack.push(pkgEntity);
		return pkgEntity;
	}

	public FunctionEntity foundMethodDeclaratorImplementation(String methodName, GenericName returnType){
		FunctionEntity functionEntity = new FunctionEntityImpl(GenericName.build(methodName), this.latestValidContainer(),
				idGenerator.generateId(),returnType);
		entityRepo.add(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		super.pushToStack(functionEntity);
		return functionEntity;
	}

	public FunctionEntity foundMethodDeclaratorProto(String methodName, GenericName returnType){
		FunctionEntity functionEntity = new FunctionEntityProto(GenericName.build(methodName), this.latestValidContainer(),
				idGenerator.generateId(),returnType);
		entityRepo.add(functionEntity);
		this.typeOrFileContainer().addFunction(functionEntity);
		super.pushToStack(functionEntity);
		return functionEntity;		
	}


	@Override
	public FileEntity startFile(String fileName) {
		currentFileEntity = new FileEntity(fileName, idGenerator.generateId(),true);
		pushToStack(currentFileEntity);
		entityRepo.add(currentFileEntity);
		return currentFileEntity;
	}
	
	
}
