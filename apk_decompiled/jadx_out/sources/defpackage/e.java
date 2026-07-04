package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class e extends uv implements gv {
    public final /* synthetic */ int l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e(int i, Object obj, Class cls, String str, String str2, int i2, int i3, int i4) {
        super(i, obj, cls, str, str2, i2, i3);
        this.l = i4;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        Object[] objArr;
        Object[] objArr2;
        int i;
        int i2 = this.l;
        x31 x31Var = x31.a;
        Object obj2 = this.f;
        switch (i2) {
            case 0:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                de deVar = (de) obj2;
                le0 le0Var = deVar.G;
                if (booleanValue) {
                    deVar.J0();
                } else {
                    ij ijVar = null;
                    if (deVar.u != null) {
                        Object[] objArr3 = le0Var.c;
                        long[] jArr = le0Var.a;
                        int length = jArr.length - 2;
                        if (length >= 0) {
                            int i3 = 0;
                            int i4 = 0;
                            while (true) {
                                long j = jArr[i4];
                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                    int i5 = 8;
                                    int i6 = 8 - ((~(i4 - length)) >>> 31);
                                    int i7 = 0;
                                    while (i7 < i6) {
                                        if ((255 & j) < 128) {
                                            i = i5;
                                            objArr2 = objArr3;
                                            f31.G(deVar.p0(), null, new j(deVar, (on0) objArr3[(i4 << 3) + i7], ijVar, i3), 3);
                                        } else {
                                            objArr2 = objArr3;
                                            i = i5;
                                        }
                                        j >>= i;
                                        i7++;
                                        i5 = i;
                                        objArr3 = objArr2;
                                    }
                                    objArr = objArr3;
                                    if (i6 != i5) {
                                    }
                                } else {
                                    objArr = objArr3;
                                }
                                if (i4 != length) {
                                    i4++;
                                    objArr3 = objArr;
                                }
                            }
                        }
                        on0 on0Var = deVar.I;
                        if (on0Var != null) {
                            f31.G(deVar.p0(), null, new j(deVar, on0Var, ijVar, 1), 3);
                        }
                    }
                    le0Var.a();
                    deVar.I = null;
                }
                return x31Var;
            default:
                ((h30) obj2).s((Throwable) obj);
                return x31Var;
        }
    }
}
