package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a7 {
    public static final a7 e;
    public static final a7 f;
    public static final /* synthetic */ a7[] g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, a7] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, a7] */
    static {
        ?? r0 = new Enum("BoundReached", 0);
        e = r0;
        ?? r1 = new Enum("Finished", 1);
        f = r1;
        g = new a7[]{r0, r1};
    }

    public static a7 valueOf(String str) {
        return (a7) Enum.valueOf(a7.class, str);
    }

    public static a7[] values() {
        return (a7[]) g.clone();
    }
}
