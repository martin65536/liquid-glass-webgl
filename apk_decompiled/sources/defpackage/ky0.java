package defpackage;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ky0 extends l0 implements os, vv, iy0, ps {
    public static final /* synthetic */ AtomicReferenceFieldUpdater j = AtomicReferenceFieldUpdater.newUpdater(ky0.class, Object.class, "_state$volatile");
    public static final /* synthetic */ long k = qc.a.objectFieldOffset(ky0.class.getDeclaredField("_state$volatile"));
    private volatile /* synthetic */ Object _state$volatile;
    public int i;

    public ky0(Object obj) {
        this._state$volatile = obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
    
        r1 = r1;
        r8 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0094, code lost:
    
        if (r13.equals(r15) != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00f4, code lost:
    
        if (r9 == r2) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007a, code lost:
    
        if (r15 != r2) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0082 A[Catch: all -> 0x0038, TryCatch #0 {all -> 0x0038, blocks: (B:13:0x0034, B:14:0x007a, B:16:0x0082, B:19:0x0089, B:20:0x008d, B:24:0x0090, B:26:0x00b1, B:29:0x00c1, B:30:0x00dd, B:36:0x00ed, B:32:0x00e4, B:35:0x00ea, B:45:0x0096, B:48:0x009d, B:56:0x004b), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c1 A[Catch: all -> 0x0038, TryCatch #0 {all -> 0x0038, blocks: (B:13:0x0034, B:14:0x007a, B:16:0x0082, B:19:0x0089, B:20:0x008d, B:24:0x0090, B:26:0x00b1, B:29:0x00c1, B:30:0x00dd, B:36:0x00ed, B:32:0x00e4, B:35:0x00ea, B:45:0x0096, B:48:0x009d, B:56:0x004b), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [ly0] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2, types: [m0] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [ly0] */
    /* JADX WARN: Type inference failed for: r1v7, types: [ly0] */
    /* JADX WARN: Type inference failed for: r1v8, types: [ly0] */
    /* JADX WARN: Type inference failed for: r8v1, types: [l0] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [ky0] */
    /* JADX WARN: Type inference failed for: r8v5, types: [ky0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v7, types: [ky0] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00c0 -> B:14:0x007a). Please report as a decompilation issue!!! */
    @Override // defpackage.os
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object b(defpackage.ps r14, defpackage.ij r15) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ky0.b(ps, ij):java.lang.Object");
    }

    @Override // defpackage.vv
    public final os c(yj yjVar, int i, xb xbVar) {
        if ((((i >= 0 && i < 2) || i == -2) && xbVar == xb.f) || ((i == 0 || i == -3) && xbVar == xb.e)) {
            return this;
        }
        return new gd(this, yjVar, i, xbVar);
    }

    @Override // defpackage.l0
    public final m0 d() {
        return new ly0();
    }

    @Override // defpackage.l0
    public final m0[] e() {
        return new ly0[2];
    }

    @Override // defpackage.ps
    public final Object g(Object obj, ij ijVar) {
        i(obj);
        return x31.a;
    }

    @Override // defpackage.iy0
    public final Object getValue() {
        wq wqVar = o4.f;
        j.getClass();
        Object objectVolatile = qc.a.getObjectVolatile(this, k);
        if (objectVolatile == wqVar) {
            return null;
        }
        return objectVolatile;
    }

    public final void i(Object obj) {
        if (obj == null) {
            obj = o4.f;
        }
        j(null, obj);
    }

    public final boolean j(Object obj, Object obj2) {
        int i;
        m0[] m0VarArr;
        wq wqVar;
        synchronized (this) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = j;
            Object obj3 = atomicReferenceFieldUpdater.get(this);
            if (obj != null && !o20.e(obj3, obj)) {
                return false;
            }
            if (o20.e(obj3, obj2)) {
                return true;
            }
            atomicReferenceFieldUpdater.set(this, obj2);
            int i2 = this.i;
            if ((i2 & 1) == 0) {
                int i3 = i2 + 1;
                this.i = i3;
                m0[] m0VarArr2 = this.e;
                while (true) {
                    ly0[] ly0VarArr = (ly0[]) m0VarArr2;
                    if (ly0VarArr != null) {
                        for (ly0 ly0Var : ly0VarArr) {
                            if (ly0Var != null) {
                                AtomicReference atomicReference = ly0Var.a;
                                while (true) {
                                    Object obj4 = atomicReference.get();
                                    if (obj4 != null && obj4 != (wqVar = o20.r)) {
                                        wq wqVar2 = o20.q;
                                        if (obj4 == wqVar2) {
                                            while (!atomicReference.compareAndSet(obj4, wqVar)) {
                                                if (atomicReference.get() != obj4) {
                                                    break;
                                                }
                                            }
                                        } else {
                                            while (!atomicReference.compareAndSet(obj4, wqVar2)) {
                                                if (atomicReference.get() != obj4) {
                                                    break;
                                                }
                                            }
                                            ((pc) obj4).u(x31.a);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    synchronized (this) {
                        i = this.i;
                        if (i == i3) {
                            this.i = i3 + 1;
                            return true;
                        }
                        m0VarArr = this.e;
                    }
                    m0VarArr2 = m0VarArr;
                    i3 = i;
                }
            } else {
                this.i = i2 + 2;
                return true;
            }
        }
    }
}
