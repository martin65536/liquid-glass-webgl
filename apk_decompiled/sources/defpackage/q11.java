package defpackage;

import java.util.HashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q11 extends bd0 implements r40, tp, qu0 {
    public HashMap A;
    public bk0 B;
    public r11 C;
    public o11 D;
    public p11 E;
    public String s;
    public r11 t;
    public wt u;
    public int v;
    public boolean w;
    public int x;
    public int y;
    public u2 z;

    public final bk0 D0() {
        r11 r11Var = this.C;
        if (r11Var == null) {
            r11Var = this.t;
        }
        r11 r11Var2 = r11Var;
        if (this.B == null) {
            this.B = new bk0(this.s, r11Var2, this.u, this.v, this.w, this.x, this.y);
        }
        bk0 bk0Var = this.B;
        bk0Var.getClass();
        return bk0Var;
    }

    public final boolean E0(int i) {
        r11 r11Var = this.C;
        r11 r11Var2 = this.t;
        d20.K(this, "StyleOuterNode", new ts0(29));
        this.C = r11Var2;
        if (r11Var == null) {
            return false;
        }
        return !r11Var.equals(r11Var2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0016, code lost:
    
        if (r1 != null) goto L15;
     */
    @Override // defpackage.tp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void R(defpackage.b50 r19) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.q11.R(b50):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:
    
        if (r1 != null) goto L19;
     */
    @Override // defpackage.r40
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.qc0 Y(defpackage.ob0 r10, defpackage.kc0 r11, long r12) {
        /*
            r9 = this;
            java.lang.String r0 = "TextStringSimpleNode::measure"
            android.os.Trace.beginSection(r0)
            r0 = 1
            boolean r1 = r9.E0(r0)     // Catch: java.lang.Throwable -> Lb9
            if (r1 == 0) goto L26
            r11 r1 = r9.C     // Catch: java.lang.Throwable -> Lb9
            if (r1 != 0) goto L12
            r11 r1 = r9.t     // Catch: java.lang.Throwable -> Lb9
        L12:
            r3 = r1
            bk0 r1 = r9.D0()     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r2 = r9.s     // Catch: java.lang.Throwable -> Lb9
            wt r4 = r9.u     // Catch: java.lang.Throwable -> Lb9
            int r5 = r9.v     // Catch: java.lang.Throwable -> Lb9
            boolean r6 = r9.w     // Catch: java.lang.Throwable -> Lb9
            int r7 = r9.x     // Catch: java.lang.Throwable -> Lb9
            int r8 = r9.y     // Catch: java.lang.Throwable -> Lb9
            r1.d(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb9
        L26:
            p11 r1 = r9.E     // Catch: java.lang.Throwable -> Lb9
            if (r1 == 0) goto L36
            boolean r2 = r1.c     // Catch: java.lang.Throwable -> Lb9
            if (r2 == 0) goto L2f
            goto L30
        L2f:
            r1 = 0
        L30:
            if (r1 == 0) goto L36
            bk0 r1 = r1.d     // Catch: java.lang.Throwable -> Lb9
            if (r1 != 0) goto L3a
        L36:
            bk0 r1 = r9.D0()     // Catch: java.lang.Throwable -> Lb9
        L3a:
            r1.c(r10)     // Catch: java.lang.Throwable -> Lb9
            m40 r2 = r10.getLayoutDirection()     // Catch: java.lang.Throwable -> Lb9
            boolean r12 = r1.a(r12, r2)     // Catch: java.lang.Throwable -> Lb9
            ak0 r13 = r1.n     // Catch: java.lang.Throwable -> Lb9
            if (r13 == 0) goto L4c
            r13.b()     // Catch: java.lang.Throwable -> Lb9
        L4c:
            t5 r13 = r1.j     // Catch: java.lang.Throwable -> Lb9
            r13.getClass()     // Catch: java.lang.Throwable -> Lb9
            f11 r13 = r13.d     // Catch: java.lang.Throwable -> Lb9
            long r1 = r1.l     // Catch: java.lang.Throwable -> Lb9
            if (r12 == 0) goto L90
            r12 = 2
            ng0 r3 = defpackage.k81.B(r9, r12)     // Catch: java.lang.Throwable -> Lb9
            r3.W0()     // Catch: java.lang.Throwable -> Lb9
            java.util.HashMap r3 = r9.A     // Catch: java.lang.Throwable -> Lb9
            if (r3 != 0) goto L6a
            java.util.HashMap r3 = new java.util.HashMap     // Catch: java.lang.Throwable -> Lb9
            r3.<init>(r12)     // Catch: java.lang.Throwable -> Lb9
            r9.A = r3     // Catch: java.lang.Throwable -> Lb9
        L6a:
            ry r12 = defpackage.z2.a     // Catch: java.lang.Throwable -> Lb9
            r4 = 0
            float r4 = r13.c(r4)     // Catch: java.lang.Throwable -> Lb9
            int r4 = java.lang.Math.round(r4)     // Catch: java.lang.Throwable -> Lb9
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> Lb9
            r3.put(r12, r4)     // Catch: java.lang.Throwable -> Lb9
            ry r12 = defpackage.z2.b     // Catch: java.lang.Throwable -> Lb9
            int r4 = r13.f     // Catch: java.lang.Throwable -> Lb9
            int r4 = r4 - r0
            float r13 = r13.c(r4)     // Catch: java.lang.Throwable -> Lb9
            int r13 = java.lang.Math.round(r13)     // Catch: java.lang.Throwable -> Lb9
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch: java.lang.Throwable -> Lb9
            r3.put(r12, r13)     // Catch: java.lang.Throwable -> Lb9
        L90:
            r12 = 32
            long r12 = r1 >> r12
            int r4 = (int) r12     // Catch: java.lang.Throwable -> Lb9
            r12 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r12 = r12 & r1
            int r5 = (int) r12     // Catch: java.lang.Throwable -> Lb9
            long r12 = defpackage.f31.y(r4, r4, r5, r5)     // Catch: java.lang.Throwable -> Lb9
            em0 r11 = r11.v(r12)     // Catch: java.lang.Throwable -> Lb9
            java.util.HashMap r6 = r9.A     // Catch: java.lang.Throwable -> Lb9
            r6.getClass()     // Catch: java.lang.Throwable -> Lb9
            k8 r8 = new k8     // Catch: java.lang.Throwable -> Lb9
            r9 = 5
            r8.<init>(r11, r9)     // Catch: java.lang.Throwable -> Lb9
            r7 = 0
            r3 = r10
            qc0 r9 = r3.B0(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb9
            android.os.Trace.endSection()
            return r9
        Lb9:
            r0 = move-exception
            r9 = r0
            android.os.Trace.endSection()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.q11.Y(ob0, kc0, long):qc0");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [o11] */
    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        o11 o11Var = this.D;
        o11 o11Var2 = o11Var;
        if (o11Var == null) {
            final int i = 0;
            ?? r0 = new gv(this) { // from class: o11
                public final /* synthetic */ q11 f;

                {
                    this.f = this;
                }

                /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                    	at jadx.core.dex.visitors.MoveInlineVisitor.processMove(MoveInlineVisitor.java:52)
                    	at jadx.core.dex.visitors.MoveInlineVisitor.moveInline(MoveInlineVisitor.java:41)
                    	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:35)
                    */
                @Override // defpackage.gv
                public final java.lang.Object e(
                /*  JADX ERROR: Method generation error
                    jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r45v0 ??
                    	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:237)
                    	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
                    	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                    */
                /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                    	at jadx.core.dex.visitors.MoveInlineVisitor.processMove(MoveInlineVisitor.java:52)
                    	at jadx.core.dex.visitors.MoveInlineVisitor.moveInline(MoveInlineVisitor.java:41)
                    */
            };
            this.D = r0;
            o11Var2 = r0;
        }
        l7 l7Var = new l7(this.s);
        t30[] t30VarArr = zu0.a;
        bv0Var.a(wu0.z, jc0.v(l7Var));
        p11 p11Var = this.E;
        if (p11Var != null) {
            boolean z = p11Var.c;
            av0 av0Var = wu0.B;
            t30[] t30VarArr2 = zu0.a;
            t30 t30Var = t30VarArr2[17];
            bv0Var.a(av0Var, Boolean.valueOf(z));
            l7 l7Var2 = new l7(p11Var.b);
            av0 av0Var2 = wu0.A;
            t30 t30Var2 = t30VarArr2[16];
            bv0Var.a(av0Var2, l7Var2);
        }
        final int i2 = 1;
        bv0Var.a(mu0.l, new n0(null, new gv(this) { // from class: o11
            public final /* synthetic */ q11 f;

            {
                this.f = this;
            }

            /*  JADX ERROR: Method generation error
                jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r45v0 ??
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:237)
                	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                */
            /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                	at jadx.core.dex.visitors.MoveInlineVisitor.processMove(MoveInlineVisitor.java:52)
                */
            @Override // defpackage.gv
            public final java.lang.Object e(
            /*  JADX ERROR: Method generation error
                jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r45v0 ??
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:237)
                	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
                */
            /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                */
        }));
        final int i3 = 2;
        bv0Var.a(mu0.m, new n0(null, new gv(this) { // from class: o11
            public final /* synthetic */ q11 f;

            {
                this.f = this;
            }

            /*  JADX ERROR: Method generation error
                jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r45v0 ??
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:237)
                	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
                */
            /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                */
            @Override // defpackage.gv
            public final java.lang.Object e(
            /*  JADX ERROR: Method generation error
                jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r45v0 ??
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:237)
                	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
                */
            /*  JADX ERROR: NullPointerException in pass: ConstructorVisitor
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(jadx.core.dex.instructions.args.InsnArg)" because "resultArg" is null
                */
        }));
        bv0Var.a(mu0.n, new n0(null, new f6(12, this)));
        bv0Var.a(mu0.a, new n0(null, o11Var2));
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }
}
