package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class kn extends q01 {
    public int g;

    public kn(int i) {
        super(0L, false);
        this.g = i;
    }

    public abstract ij c();

    public Throwable d(Object obj) {
        qf qfVar;
        if (obj instanceof qf) {
            qfVar = (qf) obj;
        } else {
            qfVar = null;
        }
        if (qfVar == null) {
            return null;
        }
        return qfVar.a;
    }

    public final void g(Throwable th) {
        o4.K(c().r(), new Error("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    public abstract Object h();

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        r4 = (defpackage.d30) r5.j(defpackage.x1.L);
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r11 = this;
            ij r0 = r11.c()     // Catch: java.lang.Throwable -> L1f
            r0.getClass()     // Catch: java.lang.Throwable -> L1f
            in r0 = (defpackage.in) r0     // Catch: java.lang.Throwable -> L1f
            jj r1 = r0.i     // Catch: java.lang.Throwable -> L1f
            java.lang.Object r0 = r0.k     // Catch: java.lang.Throwable -> L1f
            yj r2 = r1.r()     // Catch: java.lang.Throwable -> L1f
            java.lang.Object r0 = defpackage.k81.Q(r2, r0)     // Catch: java.lang.Throwable -> L1f
            wq r3 = defpackage.k81.o     // Catch: java.lang.Throwable -> L1f
            r4 = 0
            if (r0 == r3) goto L22
            v31 r3 = defpackage.f31.W(r1, r2, r0)     // Catch: java.lang.Throwable -> L1f
            goto L23
        L1f:
            r0 = move-exception
            goto L8b
        L22:
            r3 = r4
        L23:
            yj r5 = r1.r()     // Catch: java.lang.Throwable -> L46
            java.lang.Object r6 = r11.h()     // Catch: java.lang.Throwable -> L46
            java.lang.Throwable r7 = r11.d(r6)     // Catch: java.lang.Throwable -> L46
            if (r7 != 0) goto L48
            int r8 = r11.g     // Catch: java.lang.Throwable -> L46
            r9 = 1
            if (r8 == r9) goto L3b
            r10 = 2
            if (r8 != r10) goto L3a
            goto L3b
        L3a:
            r9 = 0
        L3b:
            if (r9 == 0) goto L48
            x1 r4 = defpackage.x1.L     // Catch: java.lang.Throwable -> L46
            wj r4 = r5.j(r4)     // Catch: java.lang.Throwable -> L46
            d30 r4 = (defpackage.d30) r4     // Catch: java.lang.Throwable -> L46
            goto L48
        L46:
            r1 = move-exception
            goto L7f
        L48:
            if (r4 == 0) goto L5f
            boolean r5 = r4.b()     // Catch: java.lang.Throwable -> L46
            if (r5 != 0) goto L5f
            java.util.concurrent.CancellationException r4 = r4.m()     // Catch: java.lang.Throwable -> L46
            r11.b(r4)     // Catch: java.lang.Throwable -> L46
            jq0 r4 = defpackage.o30.l(r4)     // Catch: java.lang.Throwable -> L46
            r1.u(r4)     // Catch: java.lang.Throwable -> L46
            goto L71
        L5f:
            if (r7 == 0) goto L6a
            jq0 r4 = new jq0     // Catch: java.lang.Throwable -> L46
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L46
            r1.u(r4)     // Catch: java.lang.Throwable -> L46
            goto L71
        L6a:
            java.lang.Object r4 = r11.e(r6)     // Catch: java.lang.Throwable -> L46
            r1.u(r4)     // Catch: java.lang.Throwable -> L46
        L71:
            if (r3 == 0) goto L7b
            boolean r1 = r3.p0()     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto L7a
            goto L7b
        L7a:
            return
        L7b:
            defpackage.k81.G(r2, r0)     // Catch: java.lang.Throwable -> L1f
            return
        L7f:
            if (r3 == 0) goto L87
            boolean r3 = r3.p0()     // Catch: java.lang.Throwable -> L1f
            if (r3 == 0) goto L8a
        L87:
            defpackage.k81.G(r2, r0)     // Catch: java.lang.Throwable -> L1f
        L8a:
            throw r1     // Catch: java.lang.Throwable -> L1f
        L8b:
            r11.g(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.kn.run():void");
    }

    public void b(CancellationException cancellationException) {
    }

    public Object e(Object obj) {
        return obj;
    }
}
