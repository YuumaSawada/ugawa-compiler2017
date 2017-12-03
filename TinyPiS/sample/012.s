	.section .data
	 buf: .space 8, '0'
	.byte 0x0a
	@大域変数の定義	
_Pi_var_x:
	.word 0
_Pi_var_y:
	.word 0
_Pi_var_z:
	.word 0
_Pi_var_answer:
	.word 0
	.section .text
	.global _start
_start:
	str r8, [sp, #-4]!
	ldr r8, =buf
	@式をコンパイルした命令列
	ldr r0, =#5
	ldr r1, =_Pi_var_x
	str r0, [r1, #0]
	ldr r0, =#0
	ldr r1, =_Pi_var_y
	str r0, [r1, #0]
	ldr r0, =#0
	ldr r1, =_Pi_var_z
	str r0, [r1, #0]
	ldr r0, =_Pi_var_x
	ldr r0, [r0, #0]
	cmp r0, #0
	beq L0
L2:
	ldr r0, =_Pi_var_x
	ldr r0, [r0, #0]
	cmp r0, #0
	beq L3
	ldr r0, =_Pi_var_x
	ldr r0, [r0, #0]
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#1
	sub r0, r1, r0
	ldr r1, [sp], #4
	ldr r1, =_Pi_var_x
	str r0, [r1, #0]
	ldr r0, =_Pi_var_y
	ldr r0, [r0, #0]
	str r1, [sp, #-4]!
	mov r1, r0
	ldr r0, =#2
	add r0, r1, r0
	ldr r1, [sp], #4
	ldr r1, =_Pi_var_y
	str r0, [r1, #0]
	b L2
L3:
	b L1
L0:
	ldr r0, =#0
	ldr r1, =_Pi_var_y
	str r0, [r1, #0]
L1:
	ldr r0, =_Pi_var_y
	ldr r0, [r0, #0]
	str r1, [sp, #-4]!
	str r2, [sp, #-4]!
	str r3, [sp, #-4]!
	str r4, [sp, #-4]!
	str r5, [sp, #-4]!
	str r6, [sp, #-4]!
	str r7, [sp, #-4]!
	str r8, [sp, #-4]!
	mov r1, r8
	add r1, r1, #8
	mov r6, #8
	mov r2, r0
	str r0, [sp, #-4]!
L4:
	mov r3, #16
	udiv r4, r2, r3
	mul r7, r3, r4
	sub r7, r2, r7
	cmp r7, #10
	blt L5
	b L6
L5:
	sub r1, r1, #1
	mov r2, r4
	mov r5, #48
	add r5, r5, r7
	strb r5, [r1]
	subs r6, r6, #1
	bne L4
	b L7
L6:
	sub r7, r7, #10
	sub r1, r1, #1
	mov r2, r4
	mov r5, #65
	add r5, r5, r7
	strb r5, [r1]
	subs r6, r6, #1
	bne L4
L7:
	mov r7, #4
	mov r0, #1
	mov r2, #9
	swi #0
	ldr r0, [sp], #4
	ldr r8, [sp], #4
	ldr r7, [sp], #4
	ldr r6, [sp], #4
	ldr r5, [sp], #4
	ldr r4, [sp], #4
	ldr r3, [sp], #4
	ldr r2, [sp], #4
	ldr r1, [sp], #4
	@ EXITシステムコール
	ldr r8, [sp], #4
	ldr r0, =_Pi_var_answer
	ldr r0, [r0, #0]
	mov r7, #1
	swi #0
