package defpackage;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class od extends ku0 {
    public final zb g;
    public final /* synthetic */ AtomicReferenceArray h;

    public od(long j, od odVar, zb zbVar, int i) {
        super(j, odVar, i);
        this.g = zbVar;
        this.h = new AtomicReferenceArray(bc.b * 2);
    }

    @Override // defpackage.ku0
    public final int k() {
        return bc.b;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0047, code lost:
    
        r(r5, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x004a, code lost:
    
        if (r0 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x004c, code lost:
    
        r2.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x004f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
    
        return;
     */
    @Override // defpackage.ku0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void l(int r5, defpackage.yj r6) {
        /*
            r4 = this;
            int r6 = defpackage.bc.b
            if (r5 < r6) goto L6
            r0 = 1
            goto L7
        L6:
            r0 = 0
        L7:
            if (r0 == 0) goto La
            int r5 = r5 - r6
        La:
            int r6 = r5 * 2
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r4.h
            r1.get(r6)
        L11:
            java.lang.Object r6 = r4.p(r5)
            boolean r1 = r6 instanceof defpackage.y51
            zb r2 = r4.g
            r3 = 0
            if (r1 != 0) goto L50
            boolean r1 = r6 instanceof defpackage.z51
            if (r1 == 0) goto L21
            goto L50
        L21:
            wq r1 = defpackage.bc.j
            if (r6 == r1) goto L47
            wq r1 = defpackage.bc.k
            if (r6 != r1) goto L2a
            goto L47
        L2a:
            wq r1 = defpackage.bc.g
            if (r6 == r1) goto L11
            wq r1 = defpackage.bc.f
            if (r6 != r1) goto L33
            goto L11
        L33:
            wq r4 = defpackage.bc.i
            if (r6 == r4) goto L6a
            wq r4 = defpackage.bc.d
            if (r6 != r4) goto L3c
            goto L6a
        L3c:
            wq r4 = defpackage.bc.l
            if (r6 != r4) goto L41
            goto L6a
        L41:
            java.lang.String r4 = "unexpected state: "
            defpackage.v7.e(r6, r4)
            return
        L47:
            r4.r(r5, r3)
            if (r0 == 0) goto L6a
            r2.getClass()
            return
        L50:
            if (r0 == 0) goto L55
            wq r1 = defpackage.bc.j
            goto L57
        L55:
            wq r1 = defpackage.bc.k
        L57:
            boolean r6 = r4.o(r5, r6, r1)
            if (r6 == 0) goto L11
            r4.r(r5, r3)
            r6 = r0 ^ 1
            r4.q(r5, r6)
            if (r0 == 0) goto L6a
            r2.getClass()
        L6a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.od.l(int, yj):void");
    }

    public final boolean o(int i, Object obj, Object obj2) {
        AtomicReferenceArray atomicReferenceArray;
        int i2 = (i * 2) + 1;
        do {
            atomicReferenceArray = this.h;
            if (atomicReferenceArray.compareAndSet(i2, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceArray.get(i2) == obj);
        return false;
    }

    public final Object p(int i) {
        return this.h.get((i * 2) + 1);
    }

    public final void q(int i, boolean z) {
        if (z) {
            zb zbVar = this.g;
            zbVar.getClass();
            zbVar.L((this.e * bc.b) + i);
        }
        m();
    }

    public final void r(int i, Object obj) {
        this.h.set(i * 2, obj);
    }

    public final void s(int i, Object obj) {
        this.h.set((i * 2) + 1, obj);
    }
}
