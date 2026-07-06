package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kk0 {
    public static final kk0 e;
    public static final /* synthetic */ kk0[] f;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [kk0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("CounterClockwise", 0);
        e = r0;
        f = new kk0[]{r0, new Enum("Clockwise", 1)};
    }

    public static kk0 valueOf(String str) {
        return (kk0) Enum.valueOf(kk0.class, str);
    }

    public static kk0[] values() {
        return (kk0[]) f.clone();
    }
}
