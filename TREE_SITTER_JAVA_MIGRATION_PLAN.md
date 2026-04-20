# Java Tree-sitter Frontend 执行计划（持续勾选）

## 目标与执行方式

- 保留现有 `java` 前端，不做破坏性替换。
- 持续增强 `java-ts`，直到达到可替代标准。
- 每完成一个 Java 特性项就在本文件勾选。
- 每轮都执行测试回归，确保不回退。

## 当前状态快照

- 已完成基础迁移：
  - `java-ts` 注册与处理器接入
  - package/import/type/extends/implements
  - method/constructor/field/local var
  - 基础表达式 `CALL/CREATE/CAST/SET`
  - 链式调用主链（`a().b().c()`）精确断言
- 最近一次回归：`depends.extractor.java` 全量测试通过（含 `java-ts` 新增测试）。

## 里程碑（总览）

- [x] M0: 基础迁移与接入
- [x] M1: 结构语义（类型/方法/字段/参数）
- [x] M2: 基础表达式关系
- [x] M3: Java 复杂语言特性对齐
- [x] M4: 与 `java` 前端差异收敛与替代评估报告

## Java 语言特性补齐清单（按优先级）

### P0（必须优先完成）

- [x] 参数中的嵌套调用链（如 `foo(bar().baz())`）
  - 计划测试：`JavaTreeSitterCallInArgumentsTest`
  - 样例文件：`TreeSitterCallInArgumentsSample.java`
- [x] 注解体系（声明 + 使用位点）
  - 计划测试：`JavaTreeSitterAnnotationTest`
  - 样例文件：`TreeSitterAnnotationSample.java`
- [x] 枚举细节（enum constants / enum body）
  - 计划测试：`JavaTreeSitterEnumDetailTest`
  - 样例文件：`TreeSitterEnumDetailSample.java`
- [x] `this/super` 语义引用
  - 计划测试：`JavaTreeSitterThisSuperTest`
  - 样例文件：`TreeSitterThisSuperSample.java`
- [x] `field_access` 与复杂成员访问
  - 计划测试：`JavaTreeSitterFieldAccessTest`
  - 样例文件：`TreeSitterFieldAccessSample.java`

### P1（现代 Java 高频能力）

- [x] lambda 表达式
  - 计划测试：`JavaTreeSitterLambdaTest`
  - 样例文件：`TreeSitterLambdaSample.java`
- [x] 方法引用（`::`）
  - 计划测试：`JavaTreeSitterMethodReferenceTest`
  - 样例文件：`TreeSitterMethodReferenceSample.java`
- [x] 匿名类/内部类表达式行为
  - 计划测试：`JavaTreeSitterAnonymousInnerClassTest`
  - 样例文件：`TreeSitterAnonymousInnerClassSample.java`
- [x] try-with-resources
  - 计划测试：`JavaTreeSitterTryWithResourcesTest`
  - 样例文件：`TreeSitterTryWithResourcesSample.java`
- [x] 泛型边界与通配符（`extends/super/&`）
  - 计划测试：`JavaTreeSitterGenericBoundsWildcardTest`
  - 样例文件：`TreeSitterGenericBoundsWildcardSample.java`

### P2（鲁棒性与边角）

- [x] switch expression / yield
  - 计划测试：`JavaTreeSitterSwitchExpressionTest`
  - 样例文件：`TreeSitterSwitchExpressionSample.java`
- [x] 增强 for / pattern 边角语法
  - 计划测试：`JavaTreeSitterEnhancedForAndPatternTest`
  - 样例文件：`TreeSitterEnhancedForAndPatternSample.java`
- [x] 不完整文件容错回归
  - 计划测试：`JavaTreeSitterParseErrorToleranceTest`
  - 样例文件：`TreeSitterIncompleteSample.java`

## 回归规则（每项完成后都执行）

- 目标测试：对应新增/修改的 `java-ts` 测试必须通过。
- 全量回归：`depends.extractor.java` 全量测试通过。
- 质量门禁：无新增 lints 错误。

## 执行记录

- [x] 已完成：链式调用主链对齐（`JavaTreeSitterCallChainTest`）。
- [x] 已完成：参数中的嵌套调用链（`JavaTreeSitterCallInArgumentsTest`）。
- [x] 已完成：注解体系（`JavaTreeSitterAnnotationTest`）。
- [x] 已完成：枚举细节（`JavaTreeSitterEnumDetailTest`）。
- [x] 已完成：`this/super` 语义引用（`JavaTreeSitterThisSuperTest`）。
- [x] 已完成：`field_access` 与复杂成员访问（`JavaTreeSitterFieldAccessTest`）。
- [x] 已完成：lambda 表达式（`JavaTreeSitterLambdaTest`）。
- [x] 已完成：方法引用（`JavaTreeSitterMethodReferenceTest`）。
- [x] 已完成：匿名类/内部类表达式行为（`JavaTreeSitterAnonymousInnerClassTest`）。
- [x] 已完成：try-with-resources（`JavaTreeSitterTryWithResourcesTest`）。
- [x] 已完成：泛型边界与通配符（`JavaTreeSitterGenericBoundsWildcardTest`）。
- [x] 已完成：switch expression / yield（`JavaTreeSitterSwitchExpressionTest`）。
- [x] 已完成：增强 for / pattern 边角语法（`JavaTreeSitterEnhancedForAndPatternTest`）。
- [x] 已完成：不完整文件容错回归（`JavaTreeSitterParseErrorToleranceTest`）。
- [x] 已完成：M4 差异收敛与替代评估报告（`TREE_SITTER_JAVA_M4_REPLACEMENT_REPORT.md`）。
