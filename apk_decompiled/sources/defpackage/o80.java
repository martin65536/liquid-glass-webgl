package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o80 extends s51 {
    public final he0 b;

    public o80() {
        he0 he0Var = u10.a;
        this.b = new he0();
    }

    @Override // defpackage.s51
    public final void b() {
        he0 he0Var = this.b;
        int[] iArr = he0Var.b;
        Object[] objArr = he0Var.c;
        long[] jArr = he0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            int i5 = iArr[i4];
                            pe0 pe0Var = (pe0) objArr[i4];
                            Object[] objArr2 = pe0Var.a;
                            int i6 = pe0Var.b;
                            for (int i7 = 0; i7 < i6; i7++) {
                                n80 n80Var = (n80) objArr2[i7];
                                rc rcVar = n80Var.d;
                                if (rcVar != null) {
                                    rcVar.cancel();
                                }
                                n80Var.d = null;
                                ac0 ac0Var = (ac0) n80Var.a.f;
                                ac0Var.f = true;
                                ac0Var.e = false;
                                ac0Var.a();
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        return;
                    }
                }
                if (i != length) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
