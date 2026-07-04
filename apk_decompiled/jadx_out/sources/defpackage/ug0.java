package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ug0 {
    public final hu0 a;
    public final kv b;
    public mm c;
    public boolean d;
    public final c4 e = new c4(5);

    public ug0(hu0 hu0Var, kv kvVar, mm mmVar) {
        this.a = hu0Var;
        this.b = kvVar;
        this.c = mmVar;
    }

    public static void a(pm0 pm0Var) {
        List list = pm0Var.a;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((um0) list.get(i)).a();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object b(defpackage.kv r5, defpackage.jj r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof defpackage.tg0
            if (r0 == 0) goto L13
            r0 = r6
            tg0 r0 = (defpackage.tg0) r0
            int r1 = r0.j
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.j = r1
            goto L18
        L13:
            tg0 r0 = new tg0
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.h
            int r1 = r0.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L2c
            if (r1 != r3) goto L26
            defpackage.o30.x(r6)
            goto L4d
        L26:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r4)
            return r2
        L2c:
            defpackage.o30.x(r6)
            r4.d = r3
            d r6 = new d
            r1 = 14
            r6.<init>(r4, r5, r2, r1)
            r0.j = r3
            mz0 r5 = new mz0
            yj r1 = r0.f
            r1.getClass()
            r5.<init>(r0, r1)
            java.lang.Object r5 = defpackage.o30.w(r5, r5, r6)
            ik r6 = defpackage.ik.e
            if (r5 != r6) goto L4d
            return r6
        L4d:
            r5 = 0
            r4.d = r5
            x31 r4 = defpackage.x31.a
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ug0.b(kv, jj):java.lang.Object");
    }
}
