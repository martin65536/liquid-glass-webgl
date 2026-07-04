package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class s2 extends z30 implements mv {
    public final /* synthetic */ int f;
    public final /* synthetic */ y6 g;
    public final /* synthetic */ y6 h;
    public final /* synthetic */ y6 i;
    public final /* synthetic */ hk j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ s2(y6 y6Var, y6 y6Var2, y6 y6Var3, hk hkVar, int i) {
        super(4);
        this.f = i;
        this.g = y6Var;
        this.h = y6Var2;
        this.i = y6Var3;
        this.j = hkVar;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        int i = this.f;
        x31 x31Var = x31.a;
        hk hkVar = this.j;
        y6 y6Var = this.i;
        y6 y6Var2 = this.h;
        y6 y6Var3 = this.g;
        switch (i) {
            case 0:
                long j = ((ch0) obj).a;
                long j2 = ((ch0) obj2).a;
                float floatValue = ((Number) obj3).floatValue();
                float floatValue2 = ((Number) obj4).floatValue();
                long j3 = ((ch0) y6Var3.d()).a;
                float floatValue3 = ((Number) y6Var2.d()).floatValue() * floatValue;
                float floatValue4 = ((Number) y6Var.d()).floatValue() + floatValue2;
                double d = floatValue4 * 0.017453292519943295d;
                double cos = Math.cos(d);
                double sin = Math.sin(d);
                int i2 = (int) (j2 >> 32);
                int i3 = (int) (j2 & 4294967295L);
                float intBitsToFloat = (float) ((Float.intBitsToFloat(i3) * cos) + (Float.intBitsToFloat(i2) * sin));
                f31.G(hkVar, null, new r2(this.g, ch0.g(j3, ch0.h((Float.floatToRawIntBits(intBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits((float) ((Float.intBitsToFloat(i2) * cos) - (Float.intBitsToFloat(i3) * sin))) << 32), floatValue3)), this.h, floatValue3, this.i, floatValue4, null, 0), 3);
                return x31Var;
            default:
                long j4 = ((ch0) obj).a;
                long j5 = ((ch0) obj2).a;
                float floatValue5 = ((Number) obj3).floatValue();
                float floatValue6 = ((Number) obj4).floatValue();
                long j6 = ((ch0) y6Var3.d()).a;
                float floatValue7 = ((Number) y6Var2.d()).floatValue() * floatValue5;
                float floatValue8 = ((Number) y6Var.d()).floatValue() + floatValue6;
                double d2 = floatValue8 * 0.017453292519943295d;
                double cos2 = Math.cos(d2);
                double sin2 = Math.sin(d2);
                int i4 = (int) (j5 >> 32);
                int i5 = (int) (j5 & 4294967295L);
                float intBitsToFloat2 = (float) ((Float.intBitsToFloat(i4) * cos2) - (Float.intBitsToFloat(i5) * sin2));
                float intBitsToFloat3 = (float) ((Float.intBitsToFloat(i5) * cos2) + (Float.intBitsToFloat(i4) * sin2));
                f31.G(hkVar, null, new r2(this.g, ch0.g(j6, ch0.h((Float.floatToRawIntBits(intBitsToFloat2) << 32) | (Float.floatToRawIntBits(intBitsToFloat3) & 4294967295L), floatValue7)), this.h, floatValue7, this.i, floatValue8, null, 1), 3);
                return x31Var;
        }
    }
}
