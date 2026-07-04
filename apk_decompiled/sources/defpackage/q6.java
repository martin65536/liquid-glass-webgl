package defpackage;

import android.view.Choreographer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q6 implements wj {
    public final /* synthetic */ int e;
    public final Object f;
    public final Object g;

    public q6(q6 q6Var) {
        this.e = 2;
        this.f = q6Var;
        this.g = new c9(1);
    }

    private final Object f(gv gvVar, jj jjVar) {
        n6 n6Var = (n6) this.g;
        int i = 1;
        pc pcVar = new pc(1, t20.w(jjVar));
        pcVar.s();
        p6 p6Var = new p6(pcVar, this, gvVar);
        if (o20.e(n6Var.g, (Choreographer) this.f)) {
            synchronized (n6Var.i) {
                n6Var.k.add(p6Var);
                if (!n6Var.n) {
                    n6Var.n = true;
                    n6Var.g.postFrameCallback(n6Var.o);
                }
            }
            pcVar.w(new o6(0, n6Var, p6Var));
        } else {
            ((Choreographer) this.f).postFrameCallback(p6Var);
            pcVar.w(new o6(i, this, p6Var));
        }
        return pcVar.p();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0079, code lost:
    
        if (r10 == r2) goto L32;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, z8, sb] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object d(defpackage.gv r9, defpackage.jj r10) {
        /*
            r8 = this;
            int r0 = r8.e
            r1 = 1
            switch(r0) {
                case 0: goto Lbe;
                case 1: goto L8f;
                default: goto L6;
            }
        L6:
            boolean r0 = r10 instanceof defpackage.gl0
            if (r0 == 0) goto L19
            r0 = r10
            gl0 r0 = (defpackage.gl0) r0
            int r2 = r0.k
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L19
            int r2 = r2 - r3
            r0.k = r2
            goto L1e
        L19:
            gl0 r0 = new gl0
            r0.<init>(r8, r10)
        L1e:
            java.lang.Object r10 = r0.i
            ik r2 = defpackage.ik.e
            int r3 = r0.k
            r4 = 0
            r5 = 2
            if (r3 == 0) goto L3d
            if (r3 == r1) goto L37
            if (r3 != r5) goto L30
            defpackage.o30.x(r10)
            goto L8b
        L30:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r8)
            r10 = r4
            goto L8b
        L37:
            gv r9 = r0.h
            defpackage.o30.x(r10)
            goto L7c
        L3d:
            defpackage.o30.x(r10)
            java.lang.Object r10 = r8.g
            c9 r10 = (defpackage.c9) r10
            r0.h = r9
            r0.k = r1
            boolean r3 = r10.a()
            if (r3 == 0) goto L51
            x31 r10 = defpackage.x31.a
            goto L79
        L51:
            pc r3 = new pc
            ij r6 = defpackage.t20.w(r0)
            r3.<init>(r1, r6)
            r3.s()
            java.lang.Object r6 = r10.c
            monitor-enter(r6)
            java.lang.Object r7 = r10.a     // Catch: java.lang.Throwable -> L8c
            java.util.ArrayList r7 = (java.util.ArrayList) r7     // Catch: java.lang.Throwable -> L8c
            r7.add(r3)     // Catch: java.lang.Throwable -> L8c
            monitor-exit(r6)
            zw r6 = new zw
            r6.<init>(r1, r10, r3)
            r3.w(r6)
            java.lang.Object r10 = r3.p()
            if (r10 != r2) goto L77
            goto L79
        L77:
            x31 r10 = defpackage.x31.a
        L79:
            if (r10 != r2) goto L7c
            goto L8a
        L7c:
            java.lang.Object r8 = r8.f
            q6 r8 = (defpackage.q6) r8
            r0.h = r4
            r0.k = r5
            java.lang.Object r10 = r8.d(r9, r0)
            if (r10 != r2) goto L8b
        L8a:
            r10 = r2
        L8b:
            return r10
        L8c:
            r8 = move-exception
            monitor-exit(r6)
            throw r8
        L8f:
            pc r0 = new pc
            ij r10 = defpackage.t20.w(r10)
            r0.<init>(r1, r10)
            r0.s()
            java.lang.Object r10 = r8.g
            a9 r10 = (defpackage.a9) r10
            sb r1 = new sb
            r1.<init>()
            r1.a = r0
            r1.b = r9
            java.lang.Object r8 = r8.f
            no0 r8 = (defpackage.no0) r8
            rc r8 = r10.a(r1, r8)
            tb r9 = new tb
            r10 = 0
            r9.<init>(r10, r8)
            r0.w(r9)
            java.lang.Object r8 = r0.p()
            return r8
        Lbe:
            java.lang.Object r8 = r8.f(r9, r10)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.q6.d(gv, jj):java.lang.Object");
    }

    @Override // defpackage.wj
    public final xj getKey() {
        switch (this.e) {
            case 0:
                return x1.P;
            case 1:
                return x1.P;
            default:
                return x1.P;
        }
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        switch (this.e) {
            case 0:
                return jc0.z(this, yjVar);
            case 1:
                return jc0.z(this, yjVar);
            default:
                return jc0.z(this, yjVar);
        }
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        switch (this.e) {
            case 0:
                return jc0.p(this, xjVar);
            case 1:
                return jc0.p(this, xjVar);
            default:
                return jc0.p(this, xjVar);
        }
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        switch (this.e) {
            case 0:
                return kvVar.d(obj, this);
            case 1:
                return kvVar.d(obj, this);
            default:
                return kvVar.d(obj, this);
        }
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        switch (this.e) {
            case 0:
                return jc0.x(this, xjVar);
            case 1:
                return jc0.x(this, xjVar);
            default:
                return jc0.x(this, xjVar);
        }
    }

    public q6(Choreographer choreographer, n6 n6Var) {
        this.e = 0;
        this.f = choreographer;
        this.g = n6Var;
    }

    public q6(no0 no0Var) {
        this.e = 1;
        this.f = no0Var;
        this.g = new a9();
    }
}
