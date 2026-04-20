# Java Tree-sitter Frontend M4 差异收敛与替代评估报告

## 评估范围

- 比较对象：现有 `java` 前端（JDT/ANTLR 路径）与新增 `java-ts` 前端（Tree-sitter 路径）。
- 评估维度：功能覆盖、关系抽取语义、容错性、回归稳定性、落地风险。
- 评估基线：`src/test/java/depends/extractor/java` 下回归套件与 `java-ts` 专项套件。

## 验收事实（当前仓库状态）

- `java-ts` 功能点已覆盖计划中的 P0/P1/P2 全项：
  - 结构语义：package/import/type/inherit/implement/method/constructor/field/local var
  - 表达式语义：`CALL/CREATE/CAST/SET/USE`
  - 复杂语法：注解、枚举细节、链式调用、参数嵌套调用、`this/super`、`field_access`
  - 现代特性：lambda、method reference、匿名类/内部类、try-with-resources、泛型边界/通配符
  - 边角与鲁棒：switch expression/yield、增强 for + pattern、不完整文件容错
- 最新基线回归结果：
  - `depends.extractor.java` 全量测试通过（50/50）。
  - 无新增 lints 错误。

## 与 `java` 前端的差异收敛结论

### 已收敛

- 主路径关系类型与主干语义已可稳定抽取，`java-ts` 在当前测试集合中达到可用替代能力。
- 新增特性点均有独立样例与测试，且纳入统一全量回归，持续可防回退。

### 仍需持续优化（非阻塞替代）

- 少量场景当前以“存在性断言”替代“精确目标断言”（例如部分 `switch/yield`、pattern 相关 CALL/CREATE 细粒度目标）。
- wildcard/pattern 等新语法在关系目标精度上仍有进一步收敛空间，但不影响主流程可用性与稳定性。

## 替代标准判定

在本项目当前定义的“替代标准”（功能项补齐 + 回归稳定 + 无质量回退）下，结论为：

- **`java-ts` 已达到可替代标准（工程可用级）**。

说明：

- 该结论是“工程替代”结论，而非“逐 case 完全同构（bit-level parity）”结论。
- 后续可在不阻断主线替代的前提下，继续推进关系目标精度优化。

## 建议的后续维护策略

- 默认保持 `java` 与 `java-ts` 并存一段观察期，持续对比线上样本差异。
- 若发现回归或精度差异，优先补“精确断言测试”再修解析逻辑。
- 后续新增 Java 语法支持，优先在 `java-ts` 增量落地并纳入回归矩阵。
