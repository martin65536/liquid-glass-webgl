package defpackage;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class y11 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(y11.class, "_size$volatile");
    private volatile /* synthetic */ int _size$volatile;
    public qr[] a;

    public final void a(qr qrVar) {
        qrVar.e((rr) this);
        qr[] qrVarArr = this.a;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = b;
        if (qrVarArr == null) {
            qrVarArr = new qr[4];
            this.a = qrVarArr;
        } else if (atomicIntegerFieldUpdater.get(this) >= qrVarArr.length) {
            qrVarArr = (qr[]) Arrays.copyOf(qrVarArr, atomicIntegerFieldUpdater.get(this) * 2);
            this.a = qrVarArr;
        }
        int i = atomicIntegerFieldUpdater.get(this);
        atomicIntegerFieldUpdater.set(this, i + 1);
        qrVarArr[i] = qrVar;
        qrVar.f = i;
        d(i);
    }

    public final void b(qr qrVar) {
        synchronized (this) {
            if (qrVar.b() != null) {
                c(qrVar.f);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0060, code lost:
    
        if (r6.compareTo(r7) < 0) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.qr c(int r9) {
        /*
            r8 = this;
            qr[] r0 = r8.a
            r0.getClass()
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r1 = defpackage.y11.b
            int r2 = r1.get(r8)
            r3 = -1
            int r2 = r2 + r3
            r1.set(r8, r2)
            int r2 = r1.get(r8)
            if (r9 >= r2) goto L7a
            int r2 = r1.get(r8)
            r8.e(r9, r2)
            int r2 = r9 + (-1)
            int r2 = r2 / 2
            if (r9 <= 0) goto L3a
            r4 = r0[r9]
            r4.getClass()
            r5 = r0[r2]
            r5.getClass()
            int r4 = r4.compareTo(r5)
            if (r4 >= 0) goto L3a
            r8.e(r9, r2)
            r8.d(r2)
            goto L7a
        L3a:
            int r2 = r9 * 2
            int r4 = r2 + 1
            int r5 = r1.get(r8)
            if (r4 < r5) goto L45
            goto L7a
        L45:
            qr[] r5 = r8.a
            r5.getClass()
            int r2 = r2 + 2
            int r6 = r1.get(r8)
            if (r2 >= r6) goto L63
            r6 = r5[r2]
            r6.getClass()
            r7 = r5[r4]
            r7.getClass()
            int r6 = r6.compareTo(r7)
            if (r6 >= 0) goto L63
            goto L64
        L63:
            r2 = r4
        L64:
            r4 = r5[r9]
            r4.getClass()
            r5 = r5[r2]
            r5.getClass()
            int r4 = r4.compareTo(r5)
            if (r4 > 0) goto L75
            goto L7a
        L75:
            r8.e(r9, r2)
            r9 = r2
            goto L3a
        L7a:
            int r9 = r1.get(r8)
            r9 = r0[r9]
            r9.getClass()
            r2 = 0
            r9.e(r2)
            r9.f = r3
            int r8 = r1.get(r8)
            r0[r8] = r2
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.y11.c(int):qr");
    }

    public final void d(int i) {
        while (i > 0) {
            qr[] qrVarArr = this.a;
            qrVarArr.getClass();
            int i2 = (i - 1) / 2;
            qr qrVar = qrVarArr[i2];
            qrVar.getClass();
            qr qrVar2 = qrVarArr[i];
            qrVar2.getClass();
            if (qrVar.compareTo(qrVar2) <= 0) {
                return;
            }
            e(i, i2);
            i = i2;
        }
    }

    public final void e(int i, int i2) {
        qr[] qrVarArr = this.a;
        qrVarArr.getClass();
        qr qrVar = qrVarArr[i2];
        qrVar.getClass();
        qr qrVar2 = qrVarArr[i];
        qrVar2.getClass();
        qrVarArr[i] = qrVar;
        qrVarArr[i2] = qrVar2;
        qrVar.f = i;
        qrVar2.f = i2;
    }
}
