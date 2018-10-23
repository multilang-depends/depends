package depends.extractor.cpp;

import depends.entity.repo.EntityRepo;
import depends.extractor.BuiltInTypeIdenfier;

public abstract class CppFileParser implements depends.extractor.FileParser {
	protected String fileFullPath;
	protected EntityRepo entityRepo;

	public class BuiltInType extends BuiltInTypeIdenfier {

		public BuiltInType() {
			super.createBuiltInTypes();
		}

		@Override
		public String[] getBuiltInTypeStr() {
			return new String[] { "alignas", "alignof", "asm", "auto", "bool", "break", "case", "catch", "char",
					"char16_t", "char32_t", "class", "const", "constexpr", "const_cast", "continue", "decltype",
					"default", "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit", "export", "extern",
					"false", "final", "float", "for", "friend", "goto", "if", "inline", "int", "long", "mutable",
					"namespace", "new", "noexcept", "nullptr", "operator", "override", "private", "protected", "public",
					"register", "reinterpret_cast", "return", "short", "signed", "sizeof", "static", "static_assert",
					"static_cast", "struct", "switch", "template", "this", "thread_local", "throw", "true", "try",
					"typedef", "typeid", "typename", "union", "unsigned", "using", "virtual", "void", "volatile",
					"wchar_t", "while", "<Built-in>" };
		}

		@Override
		public String[] getBuiltInPrefixStr() {
			return new String[] {};
		}

	}

	public CppFileParser(String fileFullPath, EntityRepo entityRepo) {
		this.fileFullPath = fileFullPath;
		this.entityRepo = entityRepo;
		entityRepo.setBuiltInTypeIdentifier(new BuiltInType());
	}
	
}
